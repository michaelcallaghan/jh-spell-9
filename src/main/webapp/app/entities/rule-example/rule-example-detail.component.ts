import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRuleExample } from 'app/shared/model/rule-example.model';

@Component({
  selector: 'jhi-rule-example-detail',
  templateUrl: './rule-example-detail.component.html',
})
export class RuleExampleDetailComponent implements OnInit {
  ruleExample: IRuleExample | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ruleExample }) => (this.ruleExample = ruleExample));
  }

  previousState(): void {
    window.history.back();
  }
}
