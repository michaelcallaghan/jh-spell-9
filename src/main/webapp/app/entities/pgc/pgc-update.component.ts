import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPgc, Pgc } from 'app/shared/model/pgc.model';
import { PgcService } from './pgc.service';
import { IPhoneme } from 'app/shared/model/phoneme.model';
import { PhonemeService } from 'app/entities/phoneme/phoneme.service';
import { IGrapheme } from 'app/shared/model/grapheme.model';
import { GraphemeService } from 'app/entities/grapheme/grapheme.service';
import { INationalEducationalLevel } from 'app/shared/model/national-educational-level.model';
import { NationalEducationalLevelService } from 'app/entities/national-educational-level/national-educational-level.service';

type SelectableEntity = IPhoneme | IGrapheme | INationalEducationalLevel;

@Component({
  selector: 'jhi-pgc-update',
  templateUrl: './pgc-update.component.html',
})
export class PgcUpdateComponent implements OnInit {
  isSaving = false;
  phonemes: IPhoneme[] = [];
  graphemes: IGrapheme[] = [];
  nationaleducationallevels: INationalEducationalLevel[] = [];

  editForm = this.fb.group({
    id: [],
    text: [],
    frequency: [],
    phoneme: [],
    grapheme: [],
    firstIntroducedAt: [],
  });

  constructor(
    protected pgcService: PgcService,
    protected phonemeService: PhonemeService,
    protected graphemeService: GraphemeService,
    protected nationalEducationalLevelService: NationalEducationalLevelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pgc }) => {
      this.updateForm(pgc);

      this.phonemeService.query().subscribe((res: HttpResponse<IPhoneme[]>) => (this.phonemes = res.body || []));

      this.graphemeService.query().subscribe((res: HttpResponse<IGrapheme[]>) => (this.graphemes = res.body || []));

      this.nationalEducationalLevelService
        .query()
        .subscribe((res: HttpResponse<INationalEducationalLevel[]>) => (this.nationaleducationallevels = res.body || []));
    });
  }

  updateForm(pgc: IPgc): void {
    this.editForm.patchValue({
      id: pgc.id,
      text: pgc.text,
      frequency: pgc.frequency,
      phoneme: pgc.phoneme,
      grapheme: pgc.grapheme,
      firstIntroducedAt: pgc.firstIntroducedAt,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pgc = this.createFromForm();
    if (pgc.id !== undefined) {
      this.subscribeToSaveResponse(this.pgcService.update(pgc));
    } else {
      this.subscribeToSaveResponse(this.pgcService.create(pgc));
    }
  }

  private createFromForm(): IPgc {
    return {
      ...new Pgc(),
      id: this.editForm.get(['id'])!.value,
      text: this.editForm.get(['text'])!.value,
      frequency: this.editForm.get(['frequency'])!.value,
      phoneme: this.editForm.get(['phoneme'])!.value,
      grapheme: this.editForm.get(['grapheme'])!.value,
      firstIntroducedAt: this.editForm.get(['firstIntroducedAt'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPgc>>): void {
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
