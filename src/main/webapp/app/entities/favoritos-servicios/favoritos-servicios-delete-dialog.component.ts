import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFavoritosServicios } from 'app/shared/model/favoritos-servicios.model';
import { FavoritosServiciosService } from './favoritos-servicios.service';

@Component({
  templateUrl: './favoritos-servicios-delete-dialog.component.html'
})
export class FavoritosServiciosDeleteDialogComponent {
  favoritosServicios?: IFavoritosServicios;

  constructor(
    protected favoritosServiciosService: FavoritosServiciosService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.favoritosServiciosService.delete(id).subscribe(() => {
      this.eventManager.broadcast('favoritosServiciosListModification');
      this.activeModal.close();
    });
  }
}
