import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhSpell9TestModule } from '../../../test.module';
import { GraphemeUpdateComponent } from 'app/entities/grapheme/grapheme-update.component';
import { GraphemeService } from 'app/entities/grapheme/grapheme.service';
import { Grapheme } from 'app/shared/model/grapheme.model';

describe('Component Tests', () => {
  describe('Grapheme Management Update Component', () => {
    let comp: GraphemeUpdateComponent;
    let fixture: ComponentFixture<GraphemeUpdateComponent>;
    let service: GraphemeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [GraphemeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GraphemeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GraphemeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GraphemeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Grapheme(123);
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
        const entity = new Grapheme();
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
