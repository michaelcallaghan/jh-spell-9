import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHomophonePair } from 'app/shared/model/homophone-pair.model';
import { HomophonePairService } from './homophone-pair.service';

@Component({
  templateUrl: './homophone-pair-delete-dialog.component.html',
})
export class HomophonePairDeleteDialogComponent {
  homophonePair?: IHomophonePair;

  constructor(
    protected homophonePairService: HomophonePairService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.homophonePairService.delete(id).subscribe(() => {
      this.eventManager.broadcast('homophonePairListModification');
      this.activeModal.close();
    });
  }
}
