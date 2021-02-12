import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGrapheme, Grapheme } from 'app/shared/model/grapheme.model';
import { GraphemeService } from './grapheme.service';
import { INationalEducationalLevel } from 'app/shared/model/national-educational-level.model';
import { NationalEducationalLevelService } from 'app/entities/national-educational-level/national-educational-level.service';

@Component({
  selector: 'jhi-grapheme-update',
  templateUrl: './grapheme-update.component.html',
})
export class GraphemeUpdateComponent implements OnInit {
  isSaving = false;
  nationaleducationallevels: INationalEducationalLevel[] = [];

  editForm = this.fb.group({
    id: [],
    text: [null, [Validators.required]],
    firstIntroducedAt: [],
  });

  constructor(
    protected graphemeService: GraphemeService,
    protected nationalEducationalLevelService: NationalEducationalLevelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ grapheme }) => {
      this.updateForm(grapheme);

      this.nationalEducationalLevelService
        .query()
        .subscribe((res: HttpResponse<INationalEducationalLevel[]>) => (this.nationaleducationallevels = res.body || []));
    });
  }

  updateForm(grapheme: IGrapheme): void {
    this.editForm.patchValue({
      id: grapheme.id,
      text: grapheme.text,
      firstIntroducedAt: grapheme.firstIntroducedAt,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const grapheme = this.createFromForm();
    if (grapheme.id !== undefined) {
      this.subscribeToSaveResponse(this.graphemeService.update(grapheme));
    } else {
      this.subscribeToSaveResponse(this.graphemeService.create(grapheme));
    }
  }

  private createFromForm(): IGrapheme {
    return {
      ...new Grapheme(),
      id: this.editForm.get(['id'])!.value,
      text: this.editForm.get(['text'])!.value,
      firstIntroducedAt: this.editForm.get(['firstIntroducedAt'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGrapheme>>): void {
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

  trackById(index: number, item: INationalEducationalLevel): any {
    return item.id;
  }
}
