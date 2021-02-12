import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhSpell9TestModule } from '../../../test.module';
import { PgclUpdateComponent } from 'app/entities/pgcl/pgcl-update.component';
import { PgclService } from 'app/entities/pgcl/pgcl.service';
import { Pgcl } from 'app/shared/model/pgcl.model';

describe('Component Tests', () => {
  describe('Pgcl Management Update Component', () => {
    let comp: PgclUpdateComponent;
    let fixture: ComponentFixture<PgclUpdateComponent>;
    let service: PgclService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [PgclUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PgclUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgclUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgclService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pgcl(123);
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
        const entity = new Pgcl();
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
