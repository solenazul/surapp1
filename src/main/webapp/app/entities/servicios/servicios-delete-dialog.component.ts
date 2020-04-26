import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServicios } from 'app/shared/model/servicios.model';
import { ServiciosService } from './servicios.service';

@Component({
  templateUrl: './servicios-delete-dialog.component.html'
})
export class ServiciosDeleteDialogComponent {
  servicios?: IServicios;

  constructor(protected serviciosService: ServiciosService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.serviciosService.delete(id).subscribe(() => {
      this.eventManager.broadcast('serviciosListModification');
      this.activeModal.close();
    });
  }
}
