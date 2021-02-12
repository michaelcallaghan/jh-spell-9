import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPgclo, Pgclo } from 'app/shared/model/pgclo.model';
import { PgcloService } from './pgclo.service';
import { IPgcl } from 'app/shared/model/pgcl.model';
import { PgclService } from 'app/entities/pgcl/pgcl.service';
import { IWord } from 'app/shared/model/word.model';
import { WordService } from 'app/entities/word/word.service';
import { INationalEducationalLevel } from 'app/shared/model/national-educational-level.model';
import { NationalEducationalLevelService } from 'app/entities/national-educational-level/national-educational-level.service';

type SelectableEntity = IPgcl | IWord | INationalEducationalLevel;

@Component({
  selector: 'jhi-pgclo-update',
  templateUrl: './pgclo-update.component.html',
})
export class PgcloUpdateComponent implements OnInit {
  isSaving = false;
  pgcls: IPgcl[] = [];
  words: IWord[] = [];
  nationaleducationallevels: INationalEducationalLevel[] = [];

  editForm = this.fb.group({
    id: [],
    text: [],
    start: [],
    end: [],
    pgcl: [],
    word: [],
    firstIntroducedAt: [],
  });

  constructor(
    protected pgcloService: PgcloService,
    protected pgclService: PgclService,
    protected wordService: WordService,
    protected nationalEducationalLevelService: NationalEducationalLevelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pgclo }) => {
      this.updateForm(pgclo);

      this.pgclService.query().subscribe((res: HttpResponse<IPgcl[]>) => (this.pgcls = res.body || []));

      this.wordService.query().subscribe((res: HttpResponse<IWord[]>) => (this.words = res.body || []));

      this.nationalEducationalLevelService
        .query()
        .subscribe((res: HttpResponse<INationalEducationalLevel[]>) => (this.nationaleducationallevels = res.body || []));
    });
  }

  updateForm(pgclo: IPgclo): void {
    this.editForm.patchValue({
      id: pgclo.id,
      text: pgclo.text,
      start: pgclo.start,
      end: pgclo.end,
      pgcl: pgclo.pgcl,
      word: pgclo.word,
      firstIntroducedAt: pgclo.firstIntroducedAt,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pgclo = this.createFromForm();
    if (pgclo.id !== undefined) {
      this.subscribeToSaveResponse(this.pgcloService.update(pgclo));
    } else {
      this.subscribeToSaveResponse(this.pgcloService.create(pgclo));
    }
  }

  private createFromForm(): IPgclo {
    return {
      ...new Pgclo(),
      id: this.editForm.get(['id'])!.value,
      text: this.editForm.get(['text'])!.value,
      start: this.editForm.get(['start'])!.value,
      end: this.editForm.get(['end'])!.value,
      pgcl: this.editForm.get(['pgcl'])!.value,
      word: this.editForm.get(['word'])!.value,
      firstIntroducedAt: this.editForm.get(['firstIntroducedAt'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPgclo>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
