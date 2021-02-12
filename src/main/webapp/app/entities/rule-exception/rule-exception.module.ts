import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhSpell9SharedModule } from 'app/shared/shared.module';
import { RuleExceptionComponent } from './rule-exception.component';
import { RuleExceptionDetailComponent } from './rule-exception-detail.component';
import { RuleExceptionUpdateComponent } from './rule-exception-update.component';
import { RuleExceptionDeleteDialogComponent } from './rule-exception-delete-dialog.component';
import { ruleExceptionRoute } from './rule-exception.route';

@NgModule({
  imports: [JhSpell9SharedModule, RouterModule.forChild(ruleExceptionRoute)],
  declarations: [RuleExceptionComponent, RuleExceptionDetailComponent, RuleExceptionUpdateComponent, RuleExceptionDeleteDialogComponent],
  entryComponents: [RuleExceptionDeleteDialogComponent],
})
export class JhSpell9RuleExceptionModule {}
