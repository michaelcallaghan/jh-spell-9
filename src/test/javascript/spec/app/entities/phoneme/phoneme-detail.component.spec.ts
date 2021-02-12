import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhSpell9TestModule } from '../../../test.module';
import { PhonemeDetailComponent } from 'app/entities/phoneme/phoneme-detail.component';
import { Phoneme } from 'app/shared/model/phoneme.model';

describe('Component Tests', () => {
  describe('Phoneme Management Detail Component', () => {
    let comp: PhonemeDetailComponent;
    let fixture: ComponentFixture<PhonemeDetailComponent>;
    const route = ({ data: of({ phoneme: new Phoneme(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [PhonemeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PhonemeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PhonemeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load phoneme on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.phoneme).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
