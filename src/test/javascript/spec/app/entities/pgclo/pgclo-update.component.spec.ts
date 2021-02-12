import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhSpell9TestModule } from '../../../test.module';
import { PgcloUpdateComponent } from 'app/entities/pgclo/pgclo-update.component';
import { PgcloService } from 'app/entities/pgclo/pgclo.service';
import { Pgclo } from 'app/shared/model/pgclo.model';

describe('Component Tests', () => {
  describe('Pgclo Management Update Component', () => {
    let comp: PgcloUpdateComponent;
    let fixture: ComponentFixture<PgcloUpdateComponent>;
    let service: PgcloService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [PgcloUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PgcloUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgcloUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgcloService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pgclo(123);
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
        const entity = new Pgclo();
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
