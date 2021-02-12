import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWordList } from 'app/shared/model/word-list.model';
import { WordListService } from './word-list.service';

@Component({
  templateUrl: './word-list-delete-dialog.component.html',
})
export class WordListDeleteDialogComponent {
  wordList?: IWordList;

  constructor(protected wordListService: WordListService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.wordListService.delete(id).subscribe(() => {
      this.eventManager.broadcast('wordListListModification');
      this.activeModal.close();
    });
  }
}
