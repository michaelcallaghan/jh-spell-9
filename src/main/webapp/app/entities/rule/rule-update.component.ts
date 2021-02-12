import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRule, Rule } from 'app/shared/model/rule.model';
import { RuleService } from './rule.service';
import { INationalEducationalLevel } from 'app/shared/model/national-educational-level.model';
import { NationalEducationalLevelService } from 'app/entities/national-educational-level/national-educational-level.service';

@Component({
  selector: 'jhi-rule-update',
  templateUrl: './rule-update.component.html',
})
export class RuleUpdateComponent implements OnInit {
  isSaving = false;
  nationaleducationallevels: INationalEducationalLevel[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    text: [null, [Validators.required]],
    firstIntroducedAt: [],
  });

  constructor(
    protected ruleService: RuleService,
    protected nationalEducationalLevelService: NationalEducationalLevelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rule }) => {
      this.updateForm(rule);

      this.nationalEducationalLevelService
        .query()
        .subscribe((res: HttpResponse<INationalEducationalLevel[]>) => (this.nationaleducationallevels = res.body || []));
    });
  }

  updateForm(rule: IRule): void {
    this.editForm.patchValue({
      id: rule.id,
      name: rule.name,
      text: rule.text,
      firstIntroducedAt: rule.firstIntroducedAt,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rule = this.createFromForm();
    if (rule.id !== undefined) {
      this.subscribeToSaveResponse(this.ruleService.update(rule));
    } else {
      this.subscribeToSaveResponse(this.ruleService.create(rule));
    }
  }

  private createFromForm(): IRule {
    return {
      ...new Rule(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      text: this.editForm.get(['text'])!.value,
      firstIntroducedAt: this.editForm.get(['firstIntroducedAt'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRule>>): void {
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

  trackById(index: number, item: INationalEducationalLevel): any {
    return item.id;
  }
}
