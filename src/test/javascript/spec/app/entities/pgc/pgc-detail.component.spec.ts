import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhSpell9TestModule } from '../../../test.module';
import { PgcDetailComponent } from 'app/entities/pgc/pgc-detail.component';
import { Pgc } from 'app/shared/model/pgc.model';

describe('Component Tests', () => {
  describe('Pgc Management Detail Component', () => {
    let comp: PgcDetailComponent;
    let fixture: ComponentFixture<PgcDetailComponent>;
    const route = ({ data: of({ pgc: new Pgc(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [PgcDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PgcDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgcDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pgc on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pgc).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
