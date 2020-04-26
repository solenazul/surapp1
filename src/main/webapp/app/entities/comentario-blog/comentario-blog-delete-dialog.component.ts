import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IComentarioBlog } from 'app/shared/model/comentario-blog.model';
import { ComentarioBlogService } from './comentario-blog.service';

@Component({
  templateUrl: './comentario-blog-delete-dialog.component.html'
})
export class ComentarioBlogDeleteDialogComponent {
  comentarioBlog?: IComentarioBlog;

  constructor(
    protected comentarioBlogService: ComentarioBlogService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.comentarioBlogService.delete(id).subscribe(() => {
      this.eventManager.broadcast('comentarioBlogListModification');
      this.activeModal.close();
    });
  }
}
