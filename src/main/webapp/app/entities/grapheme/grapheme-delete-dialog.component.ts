import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGrapheme } from 'app/shared/model/grapheme.model';
import { GraphemeService } from './grapheme.service';

@Component({
  templateUrl: './grapheme-delete-dialog.component.html',
})
export class GraphemeDeleteDialogComponent {
  grapheme?: IGrapheme;

  constructor(protected graphemeService: GraphemeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.graphemeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('graphemeListModification');
      this.activeModal.close();
    });
  }
}
