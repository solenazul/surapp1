import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICategorias } from 'app/shared/model/categorias.model';
import { CategoriasService } from './categorias.service';

@Component({
  templateUrl: './categorias-delete-dialog.component.html'
})
export class CategoriasDeleteDialogComponent {
  categorias?: ICategorias;

  constructor(
    protected categoriasService: CategoriasService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categoriasService.delete(id).subscribe(() => {
      this.eventManager.broadcast('categoriasListModification');
      this.activeModal.close();
    });
  }
}
