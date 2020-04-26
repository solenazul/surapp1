import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFavoritosProductos } from 'app/shared/model/favoritos-productos.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { FavoritosProductosService } from './favoritos-productos.service';
import { FavoritosProductosDeleteDialogComponent } from './favoritos-productos-delete-dialog.component';

@Component({
  selector: 'jhi-favoritos-productos',
  templateUrl: './favoritos-productos.component.html'
})
export class FavoritosProductosComponent implements OnInit, OnDestroy {
  favoritosProductos: IFavoritosProductos[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected favoritosProductosService: FavoritosProductosService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.favoritosProductos = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.favoritosProductosService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IFavoritosProductos[]>) => this.paginateFavoritosProductos(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.favoritosProductos = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFavoritosProductos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFavoritosProductos): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFavoritosProductos(): void {
    this.eventSubscriber = this.eventManager.subscribe('favoritosProductosListModification', () => this.reset());
  }

  delete(favoritosProductos: IFavoritosProductos): void {
    const modalRef = this.modalService.open(FavoritosProductosDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.favoritosProductos = favoritosProductos;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateFavoritosProductos(data: IFavoritosProductos[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.favoritosProductos.push(data[i]);
      }
    }
  }
}
