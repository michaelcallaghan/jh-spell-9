import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPhoneme } from 'app/shared/model/phoneme.model';

@Component({
  selector: 'jhi-phoneme-detail',
  templateUrl: './phoneme-detail.component.html',
})
export class PhonemeDetailComponent implements OnInit {
  phoneme: IPhoneme | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phoneme }) => (this.phoneme = phoneme));
  }

  previousState(): void {
    window.history.back();
  }
}
