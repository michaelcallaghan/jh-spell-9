import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhSpell9TestModule } from '../../../test.module';
import { RuleDetailComponent } from 'app/entities/rule/rule-detail.component';
import { Rule } from 'app/shared/model/rule.model';

describe('Component Tests', () => {
  describe('Rule Management Detail Component', () => {
    let comp: RuleDetailComponent;
    let fixture: ComponentFixture<RuleDetailComponent>;
    const route = ({ data: of({ rule: new Rule(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [RuleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RuleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RuleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load rule on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rule).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
