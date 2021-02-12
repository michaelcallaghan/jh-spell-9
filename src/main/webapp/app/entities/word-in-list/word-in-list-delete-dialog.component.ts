import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWordInList } from 'app/shared/model/word-in-list.model';
import { WordInListService } from './word-in-list.service';

@Component({
  templateUrl: './word-in-list-delete-dialog.component.html',
})
export class WordInListDeleteDialogComponent {
  wordInList?: IWordInList;

  constructor(
    protected wordInListService: WordInListService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.wordInListService.delete(id).subscribe(() => {
      this.eventManager.broadcast('wordInListListModification');
      this.activeModal.close();
    });
  }
}
