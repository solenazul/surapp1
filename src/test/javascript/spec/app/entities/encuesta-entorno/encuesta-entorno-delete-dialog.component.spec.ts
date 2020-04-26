import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Surapp1TestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { EncuestaEntornoDeleteDialogComponent } from 'app/entities/encuesta-entorno/encuesta-entorno-delete-dialog.component';
import { EncuestaEntornoService } from 'app/entities/encuesta-entorno/encuesta-entorno.service';

describe('Component Tests', () => {
  describe('EncuestaEntorno Management Delete Component', () => {
    let comp: EncuestaEntornoDeleteDialogComponent;
    let fixture: ComponentFixture<EncuestaEntornoDeleteDialogComponent>;
    let service: EncuestaEntornoService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [EncuestaEntornoDeleteDialogComponent]
      })
        .overrideTemplate(EncuestaEntornoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EncuestaEntornoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EncuestaEntornoService);
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
