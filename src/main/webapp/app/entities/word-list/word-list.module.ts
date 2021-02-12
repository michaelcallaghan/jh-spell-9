import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhSpell9SharedModule } from 'app/shared/shared.module';
import { WordListComponent } from './word-list.component';
import { WordListDetailComponent } from './word-list-detail.component';
import { WordListUpdateComponent } from './word-list-update.component';
import { WordListDeleteDialogComponent } from './word-list-delete-dialog.component';
import { wordListRoute } from './word-list.route';

@NgModule({
  imports: [JhSpell9SharedModule, RouterModule.forChild(wordListRoute)],
  declarations: [WordListComponent, WordListDetailComponent, WordListUpdateComponent, WordListDeleteDialogComponent],
  entryComponents: [WordListDeleteDialogComponent],
})
export class JhSpell9WordListModule {}
