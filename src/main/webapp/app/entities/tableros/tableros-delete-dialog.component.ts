import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITableros } from 'app/shared/model/tableros.model';
import { TablerosService } from './tableros.service';

@Component({
  templateUrl: './tableros-delete-dialog.component.html'
})
export class TablerosDeleteDialogComponent {
  tableros?: ITableros;

  constructor(protected tablerosService: TablerosService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tablerosService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tablerosListModification');
      this.activeModal.close();
    });
  }
}
