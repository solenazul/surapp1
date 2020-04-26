import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IComentariosServicios } from 'app/shared/model/comentarios-servicios.model';
import { ComentariosServiciosService } from './comentarios-servicios.service';

@Component({
  templateUrl: './comentarios-servicios-delete-dialog.component.html'
})
export class ComentariosServiciosDeleteDialogComponent {
  comentariosServicios?: IComentariosServicios;

  constructor(
    protected comentariosServiciosService: ComentariosServiciosService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.comentariosServiciosService.delete(id).subscribe(() => {
      this.eventManager.broadcast('comentariosServiciosListModification');
      this.activeModal.close();
    });
  }
}
