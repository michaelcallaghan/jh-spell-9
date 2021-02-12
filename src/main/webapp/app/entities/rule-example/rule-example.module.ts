import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhSpell9SharedModule } from 'app/shared/shared.module';
import { RuleExampleComponent } from './rule-example.component';
import { RuleExampleDetailComponent } from './rule-example-detail.component';
import { RuleExampleUpdateComponent } from './rule-example-update.component';
import { RuleExampleDeleteDialogComponent } from './rule-example-delete-dialog.component';
import { ruleExampleRoute } from './rule-example.route';

@NgModule({
  imports: [JhSpell9SharedModule, RouterModule.forChild(ruleExampleRoute)],
  declarations: [RuleExampleComponent, RuleExampleDetailComponent, RuleExampleUpdateComponent, RuleExampleDeleteDialogComponent],
  entryComponents: [RuleExampleDeleteDialogComponent],
})
export class JhSpell9RuleExampleModule {}
