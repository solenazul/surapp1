import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IModelo3D } from 'app/shared/model/modelo-3-d.model';
import { Modelo3DService } from './modelo-3-d.service';

@Component({
  templateUrl: './modelo-3-d-delete-dialog.component.html'
})
export class Modelo3DDeleteDialogComponent {
  modelo3D?: IModelo3D;

  constructor(protected modelo3DService: Modelo3DService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.modelo3DService.delete(id).subscribe(() => {
      this.eventManager.broadcast('modelo3DListModification');
      this.activeModal.close();
    });
  }
}
