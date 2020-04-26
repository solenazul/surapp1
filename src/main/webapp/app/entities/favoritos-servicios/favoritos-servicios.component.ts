import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFavoritosServicios } from 'app/shared/model/favoritos-servicios.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { FavoritosServiciosService } from './favoritos-servicios.service';
import { FavoritosServiciosDeleteDialogComponent } from './favoritos-servicios-delete-dialog.component';

@Component({
  selector: 'jhi-favoritos-servicios',
  templateUrl: './favoritos-servicios.component.html'
})
export class FavoritosServiciosComponent implements OnInit, OnDestroy {
  favoritosServicios: IFavoritosServicios[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected favoritosServiciosService: FavoritosServiciosService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.favoritosServicios = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.favoritosServiciosService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IFavoritosServicios[]>) => this.paginateFavoritosServicios(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.favoritosServicios = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFavoritosServicios();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFavoritosServicios): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFavoritosServicios(): void {
    this.eventSubscriber = this.eventManager.subscribe('favoritosServiciosListModification', () => this.reset());
  }

  delete(favoritosServicios: IFavoritosServicios): void {
    const modalRef = this.modalService.open(FavoritosServiciosDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.favoritosServicios = favoritosServicios;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateFavoritosServicios(data: IFavoritosServicios[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.favoritosServicios.push(data[i]);
      }
    }
  }
}
