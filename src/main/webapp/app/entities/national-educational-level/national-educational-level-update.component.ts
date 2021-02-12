import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INationalEducationalLevel, NationalEducationalLevel } from 'app/shared/model/national-educational-level.model';
import { NationalEducationalLevelService } from './national-educational-level.service';

@Component({
  selector: 'jhi-national-educational-level-update',
  templateUrl: './national-educational-level-update.component.html',
})
export class NationalEducationalLevelUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    level: [],
  });

  constructor(
    protected nationalEducationalLevelService: NationalEducationalLevelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nationalEducationalLevel }) => {
      this.updateForm(nationalEducationalLevel);
    });
  }

  updateForm(nationalEducationalLevel: INationalEducationalLevel): void {
    this.editForm.patchValue({
      id: nationalEducationalLevel.id,
      name: nationalEducationalLevel.name,
      description: nationalEducationalLevel.description,
      level: nationalEducationalLevel.level,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nationalEducationalLevel = this.createFromForm();
    if (nationalEducationalLevel.id !== undefined) {
      this.subscribeToSaveResponse(this.nationalEducationalLevelService.update(nationalEducationalLevel));
    } else {
      this.subscribeToSaveResponse(this.nationalEducationalLevelService.create(nationalEducationalLevel));
    }
  }

  private createFromForm(): INationalEducationalLevel {
    return {
      ...new NationalEducationalLevel(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      level: this.editForm.get(['level'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INationalEducationalLevel>>): void {
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
}
