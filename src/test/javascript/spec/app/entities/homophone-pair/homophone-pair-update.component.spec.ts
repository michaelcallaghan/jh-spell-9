import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhSpell9TestModule } from '../../../test.module';
import { HomophonePairUpdateComponent } from 'app/entities/homophone-pair/homophone-pair-update.component';
import { HomophonePairService } from 'app/entities/homophone-pair/homophone-pair.service';
import { HomophonePair } from 'app/shared/model/homophone-pair.model';

describe('Component Tests', () => {
  describe('HomophonePair Management Update Component', () => {
    let comp: HomophonePairUpdateComponent;
    let fixture: ComponentFixture<HomophonePairUpdateComponent>;
    let service: HomophonePairService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [HomophonePairUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(HomophonePairUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HomophonePairUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HomophonePairService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new HomophonePair(123);
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
        const entity = new HomophonePair();
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
