import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhSpell9SharedModule } from 'app/shared/shared.module';
import { PgclComponent } from './pgcl.component';
import { PgclDetailComponent } from './pgcl-detail.component';
import { PgclUpdateComponent } from './pgcl-update.component';
import { PgclDeleteDialogComponent } from './pgcl-delete-dialog.component';
import { pgclRoute } from './pgcl.route';

@NgModule({
  imports: [JhSpell9SharedModule, RouterModule.forChild(pgclRoute)],
  declarations: [PgclComponent, PgclDetailComponent, PgclUpdateComponent, PgclDeleteDialogComponent],
  entryComponents: [PgclDeleteDialogComponent],
})
export class JhSpell9PgclModule {}
