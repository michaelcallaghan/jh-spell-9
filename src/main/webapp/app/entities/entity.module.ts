import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'phoneme',
        loadChildren: () => import('./phoneme/phoneme.module').then(m => m.JhSpell9PhonemeModule),
      },
      {
        path: 'grapheme',
        loadChildren: () => import('./grapheme/grapheme.module').then(m => m.JhSpell9GraphemeModule),
      },
      {
        path: 'word',
        loadChildren: () => import('./word/word.module').then(m => m.JhSpell9WordModule),
      },
      {
        path: 'pgc',
        loadChildren: () => import('./pgc/pgc.module').then(m => m.JhSpell9PgcModule),
      },
      {
        path: 'pgcl',
        loadChildren: () => import('./pgcl/pgcl.module').then(m => m.JhSpell9PgclModule),
      },
      {
        path: 'pgclo',
        loadChildren: () => import('./pgclo/pgclo.module').then(m => m.JhSpell9PgcloModule),
      },
      {
        path: 'rule',
        loadChildren: () => import('./rule/rule.module').then(m => m.JhSpell9RuleModule),
      },
      {
        path: 'rule-example',
        loadChildren: () => import('./rule-example/rule-example.module').then(m => m.JhSpell9RuleExampleModule),
      },
      {
        path: 'rule-exception',
        loadChildren: () => import('./rule-exception/rule-exception.module').then(m => m.JhSpell9RuleExceptionModule),
      },
      {
        path: 'word-list',
        loadChildren: () => import('./word-list/word-list.module').then(m => m.JhSpell9WordListModule),
      },
      {
        path: 'word-in-list',
        loadChildren: () => import('./word-in-list/word-in-list.module').then(m => m.JhSpell9WordInListModule),
      },
      {
        path: 'homophone-pair',
        loadChildren: () => import('./homophone-pair/homophone-pair.module').then(m => m.JhSpell9HomophonePairModule),
      },
      {
        path: 'national-educational-level',
        loadChildren: () =>
          import('./national-educational-level/national-educational-level.module').then(m => m.JhSpell9NationalEducationalLevelModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JhSpell9EntityModule {}
