import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPgclo } from 'app/shared/model/pgclo.model';
import { PgcloService } from './pgclo.service';

@Component({
  templateUrl: './pgclo-delete-dialog.component.html',
})
export class PgcloDeleteDialogComponent {
  pgclo?: IPgclo;

  constructor(protected pgcloService: PgcloService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pgcloService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pgcloListModification');
      this.activeModal.close();
    });
  }
}
