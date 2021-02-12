import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhSpell9SharedModule } from 'app/shared/shared.module';
import { NationalEducationalLevelComponent } from './national-educational-level.component';
import { NationalEducationalLevelDetailComponent } from './national-educational-level-detail.component';
import { NationalEducationalLevelUpdateComponent } from './national-educational-level-update.component';
import { NationalEducationalLevelDeleteDialogComponent } from './national-educational-level-delete-dialog.component';
import { nationalEducationalLevelRoute } from './national-educational-level.route';

@NgModule({
  imports: [JhSpell9SharedModule, RouterModule.forChild(nationalEducationalLevelRoute)],
  declarations: [
    NationalEducationalLevelComponent,
    NationalEducationalLevelDetailComponent,
    NationalEducationalLevelUpdateComponent,
    NationalEducationalLevelDeleteDialogComponent,
  ],
  entryComponents: [NationalEducationalLevelDeleteDialogComponent],
})
export class JhSpell9NationalEducationalLevelModule {}
