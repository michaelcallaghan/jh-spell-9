import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPgc } from 'app/shared/model/pgc.model';
import { PgcService } from './pgc.service';

@Component({
  templateUrl: './pgc-delete-dialog.component.html',
})
export class PgcDeleteDialogComponent {
  pgc?: IPgc;

  constructor(protected pgcService: PgcService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pgcService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pgcListModification');
      this.activeModal.close();
    });
  }
}
