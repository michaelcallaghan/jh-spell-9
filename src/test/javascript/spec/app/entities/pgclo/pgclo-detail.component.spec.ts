import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhSpell9TestModule } from '../../../test.module';
import { PgcloDetailComponent } from 'app/entities/pgclo/pgclo-detail.component';
import { Pgclo } from 'app/shared/model/pgclo.model';

describe('Component Tests', () => {
  describe('Pgclo Management Detail Component', () => {
    let comp: PgcloDetailComponent;
    let fixture: ComponentFixture<PgcloDetailComponent>;
    const route = ({ data: of({ pgclo: new Pgclo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [PgcloDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PgcloDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgcloDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pgclo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pgclo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
