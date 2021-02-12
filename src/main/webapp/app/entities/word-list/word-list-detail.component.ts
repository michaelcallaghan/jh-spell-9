import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWordList } from 'app/shared/model/word-list.model';

@Component({
  selector: 'jhi-word-list-detail',
  templateUrl: './word-list-detail.component.html',
})
export class WordListDetailComponent implements OnInit {
  wordList: IWordList | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wordList }) => (this.wordList = wordList));
  }

  previousState(): void {
    window.history.back();
  }
}
