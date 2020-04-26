import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICategoriaTablero } from 'app/shared/model/categoria-tablero.model';
import { CategoriaTableroService } from './categoria-tablero.service';

@Component({
  templateUrl: './categoria-tablero-delete-dialog.component.html'
})
export class CategoriaTableroDeleteDialogComponent {
  categoriaTablero?: ICategoriaTablero;

  constructor(
    protected categoriaTableroService: CategoriaTableroService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categoriaTableroService.delete(id).subscribe(() => {
      this.eventManager.broadcast('categoriaTableroListModification');
      this.activeModal.close();
    });
  }
}
