import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFavoritosProductos } from 'app/shared/model/favoritos-productos.model';
import { FavoritosProductosService } from './favoritos-productos.service';

@Component({
  templateUrl: './favoritos-productos-delete-dialog.component.html'
})
export class FavoritosProductosDeleteDialogComponent {
  favoritosProductos?: IFavoritosProductos;

  constructor(
    protected favoritosProductosService: FavoritosProductosService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.favoritosProductosService.delete(id).subscribe(() => {
      this.eventManager.broadcast('favoritosProductosListModification');
      this.activeModal.close();
    });
  }
}
