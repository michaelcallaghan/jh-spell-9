import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhSpell9TestModule } from '../../../test.module';
import { WordInListDetailComponent } from 'app/entities/word-in-list/word-in-list-detail.component';
import { WordInList } from 'app/shared/model/word-in-list.model';

describe('Component Tests', () => {
  describe('WordInList Management Detail Component', () => {
    let comp: WordInListDetailComponent;
    let fixture: ComponentFixture<WordInListDetailComponent>;
    const route = ({ data: of({ wordInList: new WordInList(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [WordInListDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WordInListDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WordInListDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load wordInList on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.wordInList).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
