import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContenidoTablero } from 'app/shared/model/contenido-tablero.model';
import { ContenidoTableroService } from './contenido-tablero.service';

@Component({
  templateUrl: './contenido-tablero-delete-dialog.component.html'
})
export class ContenidoTableroDeleteDialogComponent {
  contenidoTablero?: IContenidoTablero;

  constructor(
    protected contenidoTableroService: ContenidoTableroService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contenidoTableroService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contenidoTableroListModification');
      this.activeModal.close();
    });
  }
}
