import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRuleException } from 'app/shared/model/rule-exception.model';
import { RuleExceptionService } from './rule-exception.service';

@Component({
  templateUrl: './rule-exception-delete-dialog.component.html',
})
export class RuleExceptionDeleteDialogComponent {
  ruleException?: IRuleException;

  constructor(
    protected ruleExceptionService: RuleExceptionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ruleExceptionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ruleExceptionListModification');
      this.activeModal.close();
    });
  }
}
