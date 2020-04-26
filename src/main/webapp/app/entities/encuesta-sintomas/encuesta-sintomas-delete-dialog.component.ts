import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEncuestaSintomas } from 'app/shared/model/encuesta-sintomas.model';
import { EncuestaSintomasService } from './encuesta-sintomas.service';

@Component({
  templateUrl: './encuesta-sintomas-delete-dialog.component.html'
})
export class EncuestaSintomasDeleteDialogComponent {
  encuestaSintomas?: IEncuestaSintomas;

  constructor(
    protected encuestaSintomasService: EncuestaSintomasService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.encuestaSintomasService.delete(id).subscribe(() => {
      this.eventManager.broadcast('encuestaSintomasListModification');
      this.activeModal.close();
    });
  }
}
