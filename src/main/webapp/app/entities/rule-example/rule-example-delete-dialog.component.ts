import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRuleExample } from 'app/shared/model/rule-example.model';
import { RuleExampleService } from './rule-example.service';

@Component({
  templateUrl: './rule-example-delete-dialog.component.html',
})
export class RuleExampleDeleteDialogComponent {
  ruleExample?: IRuleExample;

  constructor(
    protected ruleExampleService: RuleExampleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ruleExampleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ruleExampleListModification');
      this.activeModal.close();
    });
  }
}
