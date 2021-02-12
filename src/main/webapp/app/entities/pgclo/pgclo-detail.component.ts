import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPgclo } from 'app/shared/model/pgclo.model';

@Component({
  selector: 'jhi-pgclo-detail',
  templateUrl: './pgclo-detail.component.html',
})
export class PgcloDetailComponent implements OnInit {
  pgclo: IPgclo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pgclo }) => (this.pgclo = pgclo));
  }

  previousState(): void {
    window.history.back();
  }
}
