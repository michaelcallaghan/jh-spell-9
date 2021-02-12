import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhSpell9TestModule } from '../../../test.module';
import { HomophonePairDetailComponent } from 'app/entities/homophone-pair/homophone-pair-detail.component';
import { HomophonePair } from 'app/shared/model/homophone-pair.model';

describe('Component Tests', () => {
  describe('HomophonePair Management Detail Component', () => {
    let comp: HomophonePairDetailComponent;
    let fixture: ComponentFixture<HomophonePairDetailComponent>;
    const route = ({ data: of({ homophonePair: new HomophonePair(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [HomophonePairDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(HomophonePairDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HomophonePairDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load homophonePair on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.homophonePair).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
