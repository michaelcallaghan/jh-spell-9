import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPgcl, Pgcl } from 'app/shared/model/pgcl.model';
import { PgclService } from './pgcl.service';
import { IPgc } from 'app/shared/model/pgc.model';
import { PgcService } from 'app/entities/pgc/pgc.service';
import { INationalEducationalLevel } from 'app/shared/model/national-educational-level.model';
import { NationalEducationalLevelService } from 'app/entities/national-educational-level/national-educational-level.service';

type SelectableEntity = IPgc | INationalEducationalLevel;

@Component({
  selector: 'jhi-pgcl-update',
  templateUrl: './pgcl-update.component.html',
})
export class PgclUpdateComponent implements OnInit {
  isSaving = false;
  pgcs: IPgc[] = [];
  nationaleducationallevels: INationalEducationalLevel[] = [];

  editForm = this.fb.group({
    id: [],
    text: [],
    location: [],
    syllabicContext: [],
    frequency: [],
    pgc: [],
    firstIntroducedAt: [],
  });

  constructor(
    protected pgclService: PgclService,
    protected pgcService: PgcService,
    protected nationalEducationalLevelService: NationalEducationalLevelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pgcl }) => {
      this.updateForm(pgcl);

      this.pgcService.query().subscribe((res: HttpResponse<IPgc[]>) => (this.pgcs = res.body || []));

      this.nationalEducationalLevelService
        .query()
        .subscribe((res: HttpResponse<INationalEducationalLevel[]>) => (this.nationaleducationallevels = res.body || []));
    });
  }

  updateForm(pgcl: IPgcl): void {
    this.editForm.patchValue({
      id: pgcl.id,
      text: pgcl.text,
      location: pgcl.location,
      syllabicContext: pgcl.syllabicContext,
      frequency: pgcl.frequency,
      pgc: pgcl.pgc,
      firstIntroducedAt: pgcl.firstIntroducedAt,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pgcl = this.createFromForm();
    if (pgcl.id !== undefined) {
      this.subscribeToSaveResponse(this.pgclService.update(pgcl));
    } else {
      this.subscribeToSaveResponse(this.pgclService.create(pgcl));
    }
  }

  private createFromForm(): IPgcl {
    return {
      ...new Pgcl(),
      id: this.editForm.get(['id'])!.value,
      text: this.editForm.get(['text'])!.value,
      location: this.editForm.get(['location'])!.value,
      syllabicContext: this.editForm.get(['syllabicContext'])!.value,
      frequency: this.editForm.get(['frequency'])!.value,
      pgc: this.editForm.get(['pgc'])!.value,
      firstIntroducedAt: this.editForm.get(['firstIntroducedAt'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPgcl>>): void {
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
