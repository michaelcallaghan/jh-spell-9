import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhSpell9SharedModule } from 'app/shared/shared.module';
import { WordInListComponent } from './word-in-list.component';
import { WordInListDetailComponent } from './word-in-list-detail.component';
import { WordInListUpdateComponent } from './word-in-list-update.component';
import { WordInListDeleteDialogComponent } from './word-in-list-delete-dialog.component';
import { wordInListRoute } from './word-in-list.route';

@NgModule({
  imports: [JhSpell9SharedModule, RouterModule.forChild(wordInListRoute)],
  declarations: [WordInListComponent, WordInListDetailComponent, WordInListUpdateComponent, WordInListDeleteDialogComponent],
  entryComponents: [WordInListDeleteDialogComponent],
})
export class JhSpell9WordInListModule {}
