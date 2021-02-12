import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhSpell9TestModule } from '../../../test.module';
import { PhonemeUpdateComponent } from 'app/entities/phoneme/phoneme-update.component';
import { PhonemeService } from 'app/entities/phoneme/phoneme.service';
import { Phoneme } from 'app/shared/model/phoneme.model';

describe('Component Tests', () => {
  describe('Phoneme Management Update Component', () => {
    let comp: PhonemeUpdateComponent;
    let fixture: ComponentFixture<PhonemeUpdateComponent>;
    let service: PhonemeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [PhonemeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PhonemeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PhonemeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PhonemeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Phoneme(123);
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
        const entity = new Phoneme();
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
