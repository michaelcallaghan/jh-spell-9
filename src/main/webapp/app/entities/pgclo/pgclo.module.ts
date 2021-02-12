import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhSpell9SharedModule } from 'app/shared/shared.module';
import { PgcloComponent } from './pgclo.component';
import { PgcloDetailComponent } from './pgclo-detail.component';
import { PgcloUpdateComponent } from './pgclo-update.component';
import { PgcloDeleteDialogComponent } from './pgclo-delete-dialog.component';
import { pgcloRoute } from './pgclo.route';

@NgModule({
  imports: [JhSpell9SharedModule, RouterModule.forChild(pgcloRoute)],
  declarations: [PgcloComponent, PgcloDetailComponent, PgcloUpdateComponent, PgcloDeleteDialogComponent],
  entryComponents: [PgcloDeleteDialogComponent],
})
export class JhSpell9PgcloModule {}
