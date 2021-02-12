import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhSpell9TestModule } from '../../../test.module';
import { WordListDetailComponent } from 'app/entities/word-list/word-list-detail.component';
import { WordList } from 'app/shared/model/word-list.model';

describe('Component Tests', () => {
  describe('WordList Management Detail Component', () => {
    let comp: WordListDetailComponent;
    let fixture: ComponentFixture<WordListDetailComponent>;
    const route = ({ data: of({ wordList: new WordList(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [WordListDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WordListDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WordListDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load wordList on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.wordList).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
