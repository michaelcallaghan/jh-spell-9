import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhSpell9TestModule } from '../../../test.module';
import { RuleExceptionUpdateComponent } from 'app/entities/rule-exception/rule-exception-update.component';
import { RuleExceptionService } from 'app/entities/rule-exception/rule-exception.service';
import { RuleException } from 'app/shared/model/rule-exception.model';

describe('Component Tests', () => {
  describe('RuleException Management Update Component', () => {
    let comp: RuleExceptionUpdateComponent;
    let fixture: ComponentFixture<RuleExceptionUpdateComponent>;
    let service: RuleExceptionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [RuleExceptionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RuleExceptionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RuleExceptionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RuleExceptionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RuleException(123);
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
        const entity = new RuleException();
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
