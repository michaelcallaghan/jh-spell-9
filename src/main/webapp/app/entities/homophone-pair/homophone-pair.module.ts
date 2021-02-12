import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhSpell9SharedModule } from 'app/shared/shared.module';
import { HomophonePairComponent } from './homophone-pair.component';
import { HomophonePairDetailComponent } from './homophone-pair-detail.component';
import { HomophonePairUpdateComponent } from './homophone-pair-update.component';
import { HomophonePairDeleteDialogComponent } from './homophone-pair-delete-dialog.component';
import { homophonePairRoute } from './homophone-pair.route';

@NgModule({
  imports: [JhSpell9SharedModule, RouterModule.forChild(homophonePairRoute)],
  declarations: [HomophonePairComponent, HomophonePairDetailComponent, HomophonePairUpdateComponent, HomophonePairDeleteDialogComponent],
  entryComponents: [HomophonePairDeleteDialogComponent],
})
export class JhSpell9HomophonePairModule {}
