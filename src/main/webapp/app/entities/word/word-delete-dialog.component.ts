import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWord } from 'app/shared/model/word.model';
import { WordService } from './word.service';

@Component({
  templateUrl: './word-delete-dialog.component.html',
})
export class WordDeleteDialogComponent {
  word?: IWord;

  constructor(protected wordService: WordService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.wordService.delete(id).subscribe(() => {
      this.eventManager.broadcast('wordListModification');
      this.activeModal.close();
    });
  }
}
