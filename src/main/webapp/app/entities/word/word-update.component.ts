import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWord, Word } from 'app/shared/model/word.model';
import { WordService } from './word.service';
import { INationalEducationalLevel } from 'app/shared/model/national-educational-level.model';
import { NationalEducationalLevelService } from 'app/entities/national-educational-level/national-educational-level.service';

@Component({
  selector: 'jhi-word-update',
  templateUrl: './word-update.component.html',
})
export class WordUpdateComponent implements OnInit {
  isSaving = false;
  nationaleducationallevels: INationalEducationalLevel[] = [];

  editForm = this.fb.group({
    id: [],
    text: [null, [Validators.required]],
    ipaText: [],
    soundFile: [],
    usageSoundFile: [],
    altIpaText: [],
    altSoundFile: [],
    altUsageSoundFile: [],
    firstIntroducedAt: [],
  });

  constructor(
    protected wordService: WordService,
    protected nationalEducationalLevelService: NationalEducationalLevelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ word }) => {
      this.updateForm(word);

      this.nationalEducationalLevelService
        .query()
        .subscribe((res: HttpResponse<INationalEducationalLevel[]>) => (this.nationaleducationallevels = res.body || []));
    });
  }

  updateForm(word: IWord): void {
    this.editForm.patchValue({
      id: word.id,
      text: word.text,
      ipaText: word.ipaText,
      soundFile: word.soundFile,
      usageSoundFile: word.usageSoundFile,
      altIpaText: word.altIpaText,
      altSoundFile: word.altSoundFile,
      altUsageSoundFile: word.altUsageSoundFile,
      firstIntroducedAt: word.firstIntroducedAt,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const word = this.createFromForm();
    if (word.id !== undefined) {
      this.subscribeToSaveResponse(this.wordService.update(word));
    } else {
      this.subscribeToSaveResponse(this.wordService.create(word));
    }
  }

  private createFromForm(): IWord {
    return {
      ...new Word(),
      id: this.editForm.get(['id'])!.value,
      text: this.editForm.get(['text'])!.value,
      ipaText: this.editForm.get(['ipaText'])!.value,
      soundFile: this.editForm.get(['soundFile'])!.value,
      usageSoundFile: this.editForm.get(['usageSoundFile'])!.value,
      altIpaText: this.editForm.get(['altIpaText'])!.value,
      altSoundFile: this.editForm.get(['altSoundFile'])!.value,
      altUsageSoundFile: this.editForm.get(['altUsageSoundFile'])!.value,
      firstIntroducedAt: this.editForm.get(['firstIntroducedAt'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWord>>): void {
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
