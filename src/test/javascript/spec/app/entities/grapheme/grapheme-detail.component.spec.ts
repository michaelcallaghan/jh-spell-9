import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhSpell9TestModule } from '../../../test.module';
import { GraphemeDetailComponent } from 'app/entities/grapheme/grapheme-detail.component';
import { Grapheme } from 'app/shared/model/grapheme.model';

describe('Component Tests', () => {
  describe('Grapheme Management Detail Component', () => {
    let comp: GraphemeDetailComponent;
    let fixture: ComponentFixture<GraphemeDetailComponent>;
    const route = ({ data: of({ grapheme: new Grapheme(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [GraphemeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GraphemeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GraphemeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load grapheme on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.grapheme).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
