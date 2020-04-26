import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIPS } from 'app/shared/model/ips.model';
import { IPSService } from './ips.service';

@Component({
  templateUrl: './ips-delete-dialog.component.html'
})
export class IPSDeleteDialogComponent {
  iPS?: IIPS;

  constructor(protected iPSService: IPSService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.iPSService.delete(id).subscribe(() => {
      this.eventManager.broadcast('iPSListModification');
      this.activeModal.close();
    });
  }
}
