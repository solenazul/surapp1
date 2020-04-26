import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IComentariosProducto } from 'app/shared/model/comentarios-producto.model';
import { ComentariosProductoService } from './comentarios-producto.service';

@Component({
  templateUrl: './comentarios-producto-delete-dialog.component.html'
})
export class ComentariosProductoDeleteDialogComponent {
  comentariosProducto?: IComentariosProducto;

  constructor(
    protected comentariosProductoService: ComentariosProductoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.comentariosProductoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('comentariosProductoListModification');
      this.activeModal.close();
    });
  }
}
