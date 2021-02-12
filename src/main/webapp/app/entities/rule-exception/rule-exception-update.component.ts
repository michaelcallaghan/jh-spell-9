import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRuleException, RuleException } from 'app/shared/model/rule-exception.model';
import { RuleExceptionService } from './rule-exception.service';
import { IRule } from 'app/shared/model/rule.model';
import { RuleService } from 'app/entities/rule/rule.service';
import { IPgclo } from 'app/shared/model/pgclo.model';
import { PgcloService } from 'app/entities/pgclo/pgclo.service';
import { INationalEducationalLevel } from 'app/shared/model/national-educational-level.model';
import { NationalEducationalLevelService } from 'app/entities/national-educational-level/national-educational-level.service';

type SelectableEntity = IRule | IPgclo | INationalEducationalLevel;

@Component({
  selector: 'jhi-rule-exception-update',
  templateUrl: './rule-exception-update.component.html',
})
export class RuleExceptionUpdateComponent implements OnInit {
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
    protected ruleExceptionService: RuleExceptionService,
    protected ruleService: RuleService,
    protected pgcloService: PgcloService,
    protected nationalEducationalLevelService: NationalEducationalLevelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ruleException }) => {
      this.updateForm(ruleException);

      this.ruleService.query().subscribe((res: HttpResponse<IRule[]>) => (this.rules = res.body || []));

      this.pgcloService.query().subscribe((res: HttpResponse<IPgclo[]>) => (this.pgclos = res.body || []));

      this.nationalEducationalLevelService
        .query()
        .subscribe((res: HttpResponse<INationalEducationalLevel[]>) => (this.nationaleducationallevels = res.body || []));
    });
  }

  updateForm(ruleException: IRuleException): void {
    this.editForm.patchValue({
      id: ruleException.id,
      info: ruleException.info,
      rule: ruleException.rule,
      pgclo: ruleException.pgclo,
      firstIntroducedAt: ruleException.firstIntroducedAt,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ruleException = this.createFromForm();
    if (ruleException.id !== undefined) {
      this.subscribeToSaveResponse(this.ruleExceptionService.update(ruleException));
    } else {
      this.subscribeToSaveResponse(this.ruleExceptionService.create(ruleException));
    }
  }

  private createFromForm(): IRuleException {
    return {
      ...new RuleException(),
      id: this.editForm.get(['id'])!.value,
      info: this.editForm.get(['info'])!.value,
      rule: this.editForm.get(['rule'])!.value,
      pgclo: this.editForm.get(['pgclo'])!.value,
      firstIntroducedAt: this.editForm.get(['firstIntroducedAt'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRuleException>>): void {
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
