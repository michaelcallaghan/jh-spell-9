import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhSpell9TestModule } from '../../../test.module';
import { WordInListUpdateComponent } from 'app/entities/word-in-list/word-in-list-update.component';
import { WordInListService } from 'app/entities/word-in-list/word-in-list.service';
import { WordInList } from 'app/shared/model/word-in-list.model';

describe('Component Tests', () => {
  describe('WordInList Management Update Component', () => {
    let comp: WordInListUpdateComponent;
    let fixture: ComponentFixture<WordInListUpdateComponent>;
    let service: WordInListService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [WordInListUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WordInListUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WordInListUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WordInListService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WordInList(123);
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
        const entity = new WordInList();
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
