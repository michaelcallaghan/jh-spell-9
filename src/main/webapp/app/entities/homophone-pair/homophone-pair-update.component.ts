import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IHomophonePair, HomophonePair } from 'app/shared/model/homophone-pair.model';
import { HomophonePairService } from './homophone-pair.service';
import { IWord } from 'app/shared/model/word.model';
import { WordService } from 'app/entities/word/word.service';

@Component({
  selector: 'jhi-homophone-pair-update',
  templateUrl: './homophone-pair-update.component.html',
})
export class HomophonePairUpdateComponent implements OnInit {
  isSaving = false;
  words: IWord[] = [];

  editForm = this.fb.group({
    id: [],
    text: [],
    word: [],
    homophone: [],
  });

  constructor(
    protected homophonePairService: HomophonePairService,
    protected wordService: WordService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ homophonePair }) => {
      this.updateForm(homophonePair);

      this.wordService.query().subscribe((res: HttpResponse<IWord[]>) => (this.words = res.body || []));
    });
  }

  updateForm(homophonePair: IHomophonePair): void {
    this.editForm.patchValue({
      id: homophonePair.id,
      text: homophonePair.text,
      word: homophonePair.word,
      homophone: homophonePair.homophone,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const homophonePair = this.createFromForm();
    if (homophonePair.id !== undefined) {
      this.subscribeToSaveResponse(this.homophonePairService.update(homophonePair));
    } else {
      this.subscribeToSaveResponse(this.homophonePairService.create(homophonePair));
    }
  }

  private createFromForm(): IHomophonePair {
    return {
      ...new HomophonePair(),
      id: this.editForm.get(['id'])!.value,
      text: this.editForm.get(['text'])!.value,
      word: this.editForm.get(['word'])!.value,
      homophone: this.editForm.get(['homophone'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHomophonePair>>): void {
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

  trackById(index: number, item: IWord): any {
    return item.id;
  }
}
