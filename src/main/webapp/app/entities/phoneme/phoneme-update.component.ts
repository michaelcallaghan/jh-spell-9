import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPhoneme, Phoneme } from 'app/shared/model/phoneme.model';
import { PhonemeService } from './phoneme.service';
import { INationalEducationalLevel } from 'app/shared/model/national-educational-level.model';
import { NationalEducationalLevelService } from 'app/entities/national-educational-level/national-educational-level.service';

@Component({
  selector: 'jhi-phoneme-update',
  templateUrl: './phoneme-update.component.html',
})
export class PhonemeUpdateComponent implements OnInit {
  isSaving = false;
  nationaleducationallevels: INationalEducationalLevel[] = [];

  editForm = this.fb.group({
    id: [],
    ipa: [null, [Validators.required]],
    aka: [],
    informalUkGov: [],
    wikiIpa: [],
    firstIntroducedAt: [],
  });

  constructor(
    protected phonemeService: PhonemeService,
    protected nationalEducationalLevelService: NationalEducationalLevelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phoneme }) => {
      this.updateForm(phoneme);

      this.nationalEducationalLevelService
        .query()
        .subscribe((res: HttpResponse<INationalEducationalLevel[]>) => (this.nationaleducationallevels = res.body || []));
    });
  }

  updateForm(phoneme: IPhoneme): void {
    this.editForm.patchValue({
      id: phoneme.id,
      ipa: phoneme.ipa,
      aka: phoneme.aka,
      informalUkGov: phoneme.informalUkGov,
      wikiIpa: phoneme.wikiIpa,
      firstIntroducedAt: phoneme.firstIntroducedAt,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const phoneme = this.createFromForm();
    if (phoneme.id !== undefined) {
      this.subscribeToSaveResponse(this.phonemeService.update(phoneme));
    } else {
      this.subscribeToSaveResponse(this.phonemeService.create(phoneme));
    }
  }

  private createFromForm(): IPhoneme {
    return {
      ...new Phoneme(),
      id: this.editForm.get(['id'])!.value,
      ipa: this.editForm.get(['ipa'])!.value,
      aka: this.editForm.get(['aka'])!.value,
      informalUkGov: this.editForm.get(['informalUkGov'])!.value,
      wikiIpa: this.editForm.get(['wikiIpa'])!.value,
      firstIntroducedAt: this.editForm.get(['firstIntroducedAt'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhoneme>>): void {
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
