import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPgcl } from 'app/shared/model/pgcl.model';
import { PgclService } from './pgcl.service';

@Component({
  templateUrl: './pgcl-delete-dialog.component.html',
})
export class PgclDeleteDialogComponent {
  pgcl?: IPgcl;

  constructor(protected pgclService: PgclService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pgclService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pgclListModification');
      this.activeModal.close();
    });
  }
}
