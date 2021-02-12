import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhSpell9TestModule } from '../../../test.module';
import { RuleExampleDetailComponent } from 'app/entities/rule-example/rule-example-detail.component';
import { RuleExample } from 'app/shared/model/rule-example.model';

describe('Component Tests', () => {
  describe('RuleExample Management Detail Component', () => {
    let comp: RuleExampleDetailComponent;
    let fixture: ComponentFixture<RuleExampleDetailComponent>;
    const route = ({ data: of({ ruleExample: new RuleExample(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [RuleExampleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RuleExampleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RuleExampleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ruleExample on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ruleExample).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
