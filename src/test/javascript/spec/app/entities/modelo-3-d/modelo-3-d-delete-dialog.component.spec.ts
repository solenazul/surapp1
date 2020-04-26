import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Surapp1TestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { Modelo3DDeleteDialogComponent } from 'app/entities/modelo-3-d/modelo-3-d-delete-dialog.component';
import { Modelo3DService } from 'app/entities/modelo-3-d/modelo-3-d.service';

describe('Component Tests', () => {
  describe('Modelo3D Management Delete Component', () => {
    let comp: Modelo3DDeleteDialogComponent;
    let fixture: ComponentFixture<Modelo3DDeleteDialogComponent>;
    let service: Modelo3DService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Surapp1TestModule],
        declarations: [Modelo3DDeleteDialogComponent]
      })
        .overrideTemplate(Modelo3DDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Modelo3DDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Modelo3DService);
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
