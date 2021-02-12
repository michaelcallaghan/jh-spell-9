import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRuleException } from 'app/shared/model/rule-exception.model';

@Component({
  selector: 'jhi-rule-exception-detail',
  templateUrl: './rule-exception-detail.component.html',
})
export class RuleExceptionDetailComponent implements OnInit {
  ruleException: IRuleException | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ruleException }) => (this.ruleException = ruleException));
  }

  previousState(): void {
    window.history.back();
  }
}
