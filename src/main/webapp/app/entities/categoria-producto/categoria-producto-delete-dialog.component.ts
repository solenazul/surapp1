import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICategoriaProducto } from 'app/shared/model/categoria-producto.model';
import { CategoriaProductoService } from './categoria-producto.service';

@Component({
  templateUrl: './categoria-producto-delete-dialog.component.html'
})
export class CategoriaProductoDeleteDialogComponent {
  categoriaProducto?: ICategoriaProducto;

  constructor(
    protected categoriaProductoService: CategoriaProductoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categoriaProductoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('categoriaProductoListModification');
      this.activeModal.close();
    });
  }
}
