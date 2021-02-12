import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPgcl } from 'app/shared/model/pgcl.model';

@Component({
  selector: 'jhi-pgcl-detail',
  templateUrl: './pgcl-detail.component.html',
})
export class PgclDetailComponent implements OnInit {
  pgcl: IPgcl | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pgcl }) => (this.pgcl = pgcl));
  }

  previousState(): void {
    window.history.back();
  }
}
