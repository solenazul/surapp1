import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHistorialOfertas } from 'app/shared/model/historial-ofertas.model';
import { HistorialOfertasService } from './historial-ofertas.service';

@Component({
  templateUrl: './historial-ofertas-delete-dialog.component.html'
})
export class HistorialOfertasDeleteDialogComponent {
  historialOfertas?: IHistorialOfertas;

  constructor(
    protected historialOfertasService: HistorialOfertasService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.historialOfertasService.delete(id).subscribe(() => {
      this.eventManager.broadcast('historialOfertasListModification');
      this.activeModal.close();
    });
  }
}
