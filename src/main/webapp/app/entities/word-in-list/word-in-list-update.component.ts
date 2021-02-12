import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWordInList, WordInList } from 'app/shared/model/word-in-list.model';
import { WordInListService } from './word-in-list.service';
import { IWord } from 'app/shared/model/word.model';
import { WordService } from 'app/entities/word/word.service';
import { IWordList } from 'app/shared/model/word-list.model';
import { WordListService } from 'app/entities/word-list/word-list.service';

type SelectableEntity = IWord | IWordList;

@Component({
  selector: 'jhi-word-in-list-update',
  templateUrl: './word-in-list-update.component.html',
})
export class WordInListUpdateComponent implements OnInit {
  isSaving = false;
  words: IWord[] = [];
  wordlists: IWordList[] = [];

  editForm = this.fb.group({
    id: [],
    position: [],
    word: [],
    wordList: [],
  });

  constructor(
    protected wordInListService: WordInListService,
    protected wordService: WordService,
    protected wordListService: WordListService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wordInList }) => {
      this.updateForm(wordInList);

      this.wordService.query().subscribe((res: HttpResponse<IWord[]>) => (this.words = res.body || []));

      this.wordListService.query().subscribe((res: HttpResponse<IWordList[]>) => (this.wordlists = res.body || []));
    });
  }

  updateForm(wordInList: IWordInList): void {
    this.editForm.patchValue({
      id: wordInList.id,
      position: wordInList.position,
      word: wordInList.word,
      wordList: wordInList.wordList,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const wordInList = this.createFromForm();
    if (wordInList.id !== undefined) {
      this.subscribeToSaveResponse(this.wordInListService.update(wordInList));
    } else {
      this.subscribeToSaveResponse(this.wordInListService.create(wordInList));
    }
  }

  private createFromForm(): IWordInList {
    return {
      ...new WordInList(),
      id: this.editForm.get(['id'])!.value,
      position: this.editForm.get(['position'])!.value,
      word: this.editForm.get(['word'])!.value,
      wordList: this.editForm.get(['wordList'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWordInList>>): void {
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
