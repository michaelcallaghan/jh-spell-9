import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPhoneme } from 'app/shared/model/phoneme.model';
import { PhonemeService } from './phoneme.service';

@Component({
  templateUrl: './phoneme-delete-dialog.component.html',
})
export class PhonemeDeleteDialogComponent {
  phoneme?: IPhoneme;

  constructor(protected phonemeService: PhonemeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.phonemeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('phonemeListModification');
      this.activeModal.close();
    });
  }
}
