import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategoriaProducto } from 'app/shared/model/categoria-producto.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CategoriaProductoService } from './categoria-producto.service';
import { CategoriaProductoDeleteDialogComponent } from './categoria-producto-delete-dialog.component';

@Component({
  selector: 'jhi-categoria-producto',
  templateUrl: './categoria-producto.component.html'
})
export class CategoriaProductoComponent implements OnInit, OnDestroy {
  categoriaProductos: ICategoriaProducto[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected categoriaProductoService: CategoriaProductoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.categoriaProductos = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.categoriaProductoService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICategoriaProducto[]>) => this.paginateCategoriaProductos(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.categoriaProductos = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCategoriaProductos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICategoriaProducto): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCategoriaProductos(): void {
    this.eventSubscriber = this.eventManager.subscribe('categoriaProductoListModification', () => this.reset());
  }

  delete(categoriaProducto: ICategoriaProducto): void {
    const modalRef = this.modalService.open(CategoriaProductoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.categoriaProducto = categoriaProducto;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCategoriaProductos(data: ICategoriaProducto[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.categoriaProductos.push(data[i]);
      }
    }
  }
}
