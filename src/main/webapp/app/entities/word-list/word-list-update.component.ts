import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWordList, WordList } from 'app/shared/model/word-list.model';
import { WordListService } from './word-list.service';

@Component({
  selector: 'jhi-word-list-update',
  templateUrl: './word-list-update.component.html',
})
export class WordListUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
  });

  constructor(protected wordListService: WordListService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wordList }) => {
      this.updateForm(wordList);
    });
  }

  updateForm(wordList: IWordList): void {
    this.editForm.patchValue({
      id: wordList.id,
      name: wordList.name,
      description: wordList.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const wordList = this.createFromForm();
    if (wordList.id !== undefined) {
      this.subscribeToSaveResponse(this.wordListService.update(wordList));
    } else {
      this.subscribeToSaveResponse(this.wordListService.create(wordList));
    }
  }

  private createFromForm(): IWordList {
    return {
      ...new WordList(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWordList>>): void {
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
