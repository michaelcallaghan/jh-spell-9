import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPgc } from 'app/shared/model/pgc.model';

@Component({
  selector: 'jhi-pgc-detail',
  templateUrl: './pgc-detail.component.html',
})
export class PgcDetailComponent implements OnInit {
  pgc: IPgc | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pgc }) => (this.pgc = pgc));
  }

  previousState(): void {
    window.history.back();
  }
}
