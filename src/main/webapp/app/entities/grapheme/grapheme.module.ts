import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhSpell9SharedModule } from 'app/shared/shared.module';
import { GraphemeComponent } from './grapheme.component';
import { GraphemeDetailComponent } from './grapheme-detail.component';
import { GraphemeUpdateComponent } from './grapheme-update.component';
import { GraphemeDeleteDialogComponent } from './grapheme-delete-dialog.component';
import { graphemeRoute } from './grapheme.route';

@NgModule({
  imports: [JhSpell9SharedModule, RouterModule.forChild(graphemeRoute)],
  declarations: [GraphemeComponent, GraphemeDetailComponent, GraphemeUpdateComponent, GraphemeDeleteDialogComponent],
  entryComponents: [GraphemeDeleteDialogComponent],
})
export class JhSpell9GraphemeModule {}
