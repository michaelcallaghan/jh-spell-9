import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhSpell9TestModule } from '../../../test.module';
import { PgcUpdateComponent } from 'app/entities/pgc/pgc-update.component';
import { PgcService } from 'app/entities/pgc/pgc.service';
import { Pgc } from 'app/shared/model/pgc.model';

describe('Component Tests', () => {
  describe('Pgc Management Update Component', () => {
    let comp: PgcUpdateComponent;
    let fixture: ComponentFixture<PgcUpdateComponent>;
    let service: PgcService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [PgcUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PgcUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgcUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgcService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pgc(123);
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
        const entity = new Pgc();
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
