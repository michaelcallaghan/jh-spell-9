import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhSpell9TestModule } from '../../../test.module';
import { NationalEducationalLevelDetailComponent } from 'app/entities/national-educational-level/national-educational-level-detail.component';
import { NationalEducationalLevel } from 'app/shared/model/national-educational-level.model';

describe('Component Tests', () => {
  describe('NationalEducationalLevel Management Detail Component', () => {
    let comp: NationalEducationalLevelDetailComponent;
    let fixture: ComponentFixture<NationalEducationalLevelDetailComponent>;
    const route = ({ data: of({ nationalEducationalLevel: new NationalEducationalLevel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [NationalEducationalLevelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(NationalEducationalLevelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NationalEducationalLevelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load nationalEducationalLevel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nationalEducationalLevel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
