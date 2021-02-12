import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhSpell9TestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { NationalEducationalLevelDeleteDialogComponent } from 'app/entities/national-educational-level/national-educational-level-delete-dialog.component';
import { NationalEducationalLevelService } from 'app/entities/national-educational-level/national-educational-level.service';

describe('Component Tests', () => {
  describe('NationalEducationalLevel Management Delete Component', () => {
    let comp: NationalEducationalLevelDeleteDialogComponent;
    let fixture: ComponentFixture<NationalEducationalLevelDeleteDialogComponent>;
    let service: NationalEducationalLevelService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhSpell9TestModule],
        declarations: [NationalEducationalLevelDeleteDialogComponent],
      })
        .overrideTemplate(NationalEducationalLevelDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NationalEducationalLevelDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NationalEducationalLevelService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
