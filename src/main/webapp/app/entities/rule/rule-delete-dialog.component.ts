import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRule } from 'app/shared/model/rule.model';
import { RuleService } from './rule.service';

@Component({
  templateUrl: './rule-delete-dialog.component.html',
})
export class RuleDeleteDialogComponent {
  rule?: IRule;

  constructor(protected ruleService: RuleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ruleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ruleListModification');
      this.activeModal.close();
    });
  }
}
