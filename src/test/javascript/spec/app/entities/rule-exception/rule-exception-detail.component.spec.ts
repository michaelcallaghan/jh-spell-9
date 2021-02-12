import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhSpell9TestModule } from '../../../test.module';
import { RuleExceptionDetailComponent } from 'app/entities/rule-exception/rule-exception-detail.component';
import { RuleException } from 'app/shared/model/rule-exception.model';

describe('Component Tests', () => {
  describe('RuleException Management Detail Component', () => {
    let comp: RuleExceptionDetailComponent;
    let fixture: ComponentFixture<RuleExceptionDetailComponent>;
    const route = ({ data: of({ ruleException: new RuleException(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [RuleExceptionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RuleExceptionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RuleExceptionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ruleException on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ruleException).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
