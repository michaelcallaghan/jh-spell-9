import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhSpell9SharedModule } from 'app/shared/shared.module';
import { PgcComponent } from './pgc.component';
import { PgcDetailComponent } from './pgc-detail.component';
import { PgcUpdateComponent } from './pgc-update.component';
import { PgcDeleteDialogComponent } from './pgc-delete-dialog.component';
import { pgcRoute } from './pgc.route';

@NgModule({
  imports: [JhSpell9SharedModule, RouterModule.forChild(pgcRoute)],
  declarations: [PgcComponent, PgcDetailComponent, PgcUpdateComponent, PgcDeleteDialogComponent],
  entryComponents: [PgcDeleteDialogComponent],
})
export class JhSpell9PgcModule {}
