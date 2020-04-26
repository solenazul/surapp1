import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAlarma } from 'app/shared/model/alarma.model';
import { AlarmaService } from './alarma.service';

@Component({
  templateUrl: './alarma-delete-dialog.component.html'
})
export class AlarmaDeleteDialogComponent {
  alarma?: IAlarma;

  constructor(protected alarmaService: AlarmaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.alarmaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('alarmaListModification');
      this.activeModal.close();
    });
  }
}
