import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRuleExample, RuleExample } from 'app/shared/model/rule-example.model';
import { RuleExampleService } from './rule-example.service';
import { IRule } from 'app/shared/model/rule.model';
import { RuleService } from 'app/entities/rule/rule.service';
import { IPgclo } from 'app/shared/model/pgclo.model';
import { PgcloService } from 'app/entities/pgclo/pgclo.service';
import { INationalEducationalLevel } from 'app/shared/model/national-educational-level.model';
import { NationalEducationalLevelService } from 'app/entities/national-educational-level/national-educational-level.service';

type SelectableEntity = IRule | IPgclo | INationalEducationalLevel;

@Component({
  selector: 'jhi-rule-example-update',
  templateUrl: './rule-example-update.component.html',
})
export class RuleExampleUpdateComponent implements OnInit {
  isSaving = false;
  rules: IRule[] = [];
  pgclos: IPgclo[] = [];
  nationaleducationallevels: INationalEducationalLevel[] = [];

  editForm = this.fb.group({
    id: [],
    info: [],
    rule: [],
    pgclo: [],
    firstIntroducedAt: [],
  });

  constructor(
    protected ruleExampleService: RuleExampleService,
    protected ruleService: RuleService,
    protected pgcloService: PgcloService,
    protected nationalEducationalLevelService: NationalEducationalLevelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ruleExample }) => {
      this.updateForm(ruleExample);

      this.ruleService.query().subscribe((res: HttpResponse<IRule[]>) => (this.rules = res.body || []));

      this.pgcloService.query().subscribe((res: HttpResponse<IPgclo[]>) => (this.pgclos = res.body || []));

      this.nationalEducationalLevelService
        .query()
        .subscribe((res: HttpResponse<INationalEducationalLevel[]>) => (this.nationaleducationallevels = res.body || []));
    });
  }

  updateForm(ruleExample: IRuleExample): void {
    this.editForm.patchValue({
      id: ruleExample.id,
      info: ruleExample.info,
      rule: ruleExample.rule,
      pgclo: ruleExample.pgclo,
      firstIntroducedAt: ruleExample.firstIntroducedAt,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ruleExample = this.createFromForm();
    if (ruleExample.id !== undefined) {
      this.subscribeToSaveResponse(this.ruleExampleService.update(ruleExample));
    } else {
      this.subscribeToSaveResponse(this.ruleExampleService.create(ruleExample));
    }
  }

  private createFromForm(): IRuleExample {
    return {
      ...new RuleExample(),
      id: this.editForm.get(['id'])!.value,
      info: this.editForm.get(['info'])!.value,
      rule: this.editForm.get(['rule'])!.value,
      pgclo: this.editForm.get(['pgclo'])!.value,
      firstIntroducedAt: this.editForm.get(['firstIntroducedAt'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRuleExample>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
