import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEncuestaEntorno } from 'app/shared/model/encuesta-entorno.model';
import { EncuestaEntornoService } from './encuesta-entorno.service';

@Component({
  templateUrl: './encuesta-entorno-delete-dialog.component.html'
})
export class EncuestaEntornoDeleteDialogComponent {
  encuestaEntorno?: IEncuestaEntorno;

  constructor(
    protected encuestaEntornoService: EncuestaEntornoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.encuestaEntornoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('encuestaEntornoListModification');
      this.activeModal.close();
    });
  }
}
