import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhSpell9TestModule } from '../../../test.module';
import { RuleExampleUpdateComponent } from 'app/entities/rule-example/rule-example-update.component';
import { RuleExampleService } from 'app/entities/rule-example/rule-example.service';
import { RuleExample } from 'app/shared/model/rule-example.model';

describe('Component Tests', () => {
  describe('RuleExample Management Update Component', () => {
    let comp: RuleExampleUpdateComponent;
    let fixture: ComponentFixture<RuleExampleUpdateComponent>;
    let service: RuleExampleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [RuleExampleUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RuleExampleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RuleExampleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RuleExampleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RuleExample(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new RuleExample();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
