import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IComentariosProducto } from 'app/shared/model/comentarios-producto.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ComentariosProductoService } from './comentarios-producto.service';
import { ComentariosProductoDeleteDialogComponent } from './comentarios-producto-delete-dialog.component';

@Component({
  selector: 'jhi-comentarios-producto',
  templateUrl: './comentarios-producto.component.html'
})
export class ComentariosProductoComponent implements OnInit, OnDestroy {
  comentariosProductos: IComentariosProducto[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected comentariosProductoService: ComentariosProductoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.comentariosProductos = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.comentariosProductoService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IComentariosProducto[]>) => this.paginateComentariosProductos(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.comentariosProductos = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInComentariosProductos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IComentariosProducto): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInComentariosProductos(): void {
    this.eventSubscriber = this.eventManager.subscribe('comentariosProductoListModification', () => this.reset());
  }

  delete(comentariosProducto: IComentariosProducto): void {
    const modalRef = this.modalService.open(ComentariosProductoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.comentariosProducto = comentariosProducto;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateComentariosProductos(data: IComentariosProducto[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.comentariosProductos.push(data[i]);
      }
    }
  }
}
