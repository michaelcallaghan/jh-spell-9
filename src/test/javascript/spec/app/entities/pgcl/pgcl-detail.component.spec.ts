import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhSpell9TestModule } from '../../../test.module';
import { PgclDetailComponent } from 'app/entities/pgcl/pgcl-detail.component';
import { Pgcl } from 'app/shared/model/pgcl.model';

describe('Component Tests', () => {
  describe('Pgcl Management Detail Component', () => {
    let comp: PgclDetailComponent;
    let fixture: ComponentFixture<PgclDetailComponent>;
    const route = ({ data: of({ pgcl: new Pgcl(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [PgclDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PgclDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PgclDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pgcl on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pgcl).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
