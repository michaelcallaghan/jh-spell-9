import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhSpell9TestModule } from '../../../test.module';
import { WordListUpdateComponent } from 'app/entities/word-list/word-list-update.component';
import { WordListService } from 'app/entities/word-list/word-list.service';
import { WordList } from 'app/shared/model/word-list.model';

describe('Component Tests', () => {
  describe('WordList Management Update Component', () => {
    let comp: WordListUpdateComponent;
    let fixture: ComponentFixture<WordListUpdateComponent>;
    let service: WordListService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [WordListUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WordListUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WordListUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WordListService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WordList(123);
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
        const entity = new WordList();
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
