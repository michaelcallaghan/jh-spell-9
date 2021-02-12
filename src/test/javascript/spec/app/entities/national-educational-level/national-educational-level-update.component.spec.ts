import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhSpell9TestModule } from '../../../test.module';
import { NationalEducationalLevelUpdateComponent } from 'app/entities/national-educational-level/national-educational-level-update.component';
import { NationalEducationalLevelService } from 'app/entities/national-educational-level/national-educational-level.service';
import { NationalEducationalLevel } from 'app/shared/model/national-educational-level.model';

describe('Component Tests', () => {
  describe('NationalEducationalLevel Management Update Component', () => {
    let comp: NationalEducationalLevelUpdateComponent;
    let fixture: ComponentFixture<NationalEducationalLevelUpdateComponent>;
    let service: NationalEducationalLevelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [NationalEducationalLevelUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(NationalEducationalLevelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NationalEducationalLevelUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NationalEducationalLevelService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NationalEducationalLevel(123);
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
        const entity = new NationalEducationalLevel();
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
