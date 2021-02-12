import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INationalEducationalLevel } from 'app/shared/model/national-educational-level.model';
import { NationalEducationalLevelService } from './national-educational-level.service';

@Component({
  templateUrl: './national-educational-level-delete-dialog.component.html',
})
export class NationalEducationalLevelDeleteDialogComponent {
  nationalEducationalLevel?: INationalEducationalLevel;

  constructor(
    protected nationalEducationalLevelService: NationalEducationalLevelService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nationalEducationalLevelService.delete(id).subscribe(() => {
      this.eventManager.broadcast('nationalEducationalLevelListModification');
      this.activeModal.close();
    });
  }
}
