import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGrapheme } from 'app/shared/model/grapheme.model';

@Component({
  selector: 'jhi-grapheme-detail',
  templateUrl: './grapheme-detail.component.html',
})
export class GraphemeDetailComponent implements OnInit {
  grapheme: IGrapheme | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ grapheme }) => (this.grapheme = grapheme));
  }

  previousState(): void {
    window.history.back();
  }
}
