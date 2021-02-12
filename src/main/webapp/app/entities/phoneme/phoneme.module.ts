import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhSpell9SharedModule } from 'app/shared/shared.module';
import { PhonemeComponent } from './phoneme.component';
import { PhonemeDetailComponent } from './phoneme-detail.component';
import { PhonemeUpdateComponent } from './phoneme-update.component';
import { PhonemeDeleteDialogComponent } from './phoneme-delete-dialog.component';
import { phonemeRoute } from './phoneme.route';

@NgModule({
  imports: [JhSpell9SharedModule, RouterModule.forChild(phonemeRoute)],
  declarations: [PhonemeComponent, PhonemeDetailComponent, PhonemeUpdateComponent, PhonemeDeleteDialogComponent],
  entryComponents: [PhonemeDeleteDialogComponent],
})
export class JhSpell9PhonemeModule {}
