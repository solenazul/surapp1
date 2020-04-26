import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInvitacionTablero } from 'app/shared/model/invitacion-tablero.model';
import { InvitacionTableroService } from './invitacion-tablero.service';

@Component({
  templateUrl: './invitacion-tablero-delete-dialog.component.html'
})
export class InvitacionTableroDeleteDialogComponent {
  invitacionTablero?: IInvitacionTablero;

  constructor(
    protected invitacionTableroService: InvitacionTableroService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.invitacionTableroService.delete(id).subscribe(() => {
      this.eventManager.broadcast('invitacionTableroListModification');
      this.activeModal.close();
    });
  }
}
