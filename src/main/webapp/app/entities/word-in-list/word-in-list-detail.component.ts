import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWordInList } from 'app/shared/model/word-in-list.model';

@Component({
  selector: 'jhi-word-in-list-detail',
  templateUrl: './word-in-list-detail.component.html',
})
export class WordInListDetailComponent implements OnInit {
  wordInList: IWordInList | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wordInList }) => (this.wordInList = wordInList));
  }

  previousState(): void {
    window.history.back();
  }
}
