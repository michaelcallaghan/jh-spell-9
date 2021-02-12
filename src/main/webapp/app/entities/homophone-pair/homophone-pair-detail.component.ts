import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHomophonePair } from 'app/shared/model/homophone-pair.model';

@Component({
  selector: 'jhi-homophone-pair-detail',
  templateUrl: './homophone-pair-detail.component.html',
})
export class HomophonePairDetailComponent implements OnInit {
  homophonePair: IHomophonePair | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ homophonePair }) => (this.homophonePair = homophonePair));
  }

  previousState(): void {
    window.history.back();
  }
}
