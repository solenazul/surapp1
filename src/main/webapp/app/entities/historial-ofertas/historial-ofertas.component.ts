import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHistorialOfertas } from 'app/shared/model/historial-ofertas.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { HistorialOfertasService } from './historial-ofertas.service';
import { HistorialOfertasDeleteDialogComponent } from './historial-ofertas-delete-dialog.component';

@Component({
  selector: 'jhi-historial-ofertas',
  templateUrl: './historial-ofertas.component.html'
})
export class HistorialOfertasComponent implements OnInit, OnDestroy {
  historialOfertas: IHistorialOfertas[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected historialOfertasService: HistorialOfertasService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.historialOfertas = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.historialOfertasService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IHistorialOfertas[]>) => this.paginateHistorialOfertas(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.historialOfertas = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInHistorialOfertas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IHistorialOfertas): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInHistorialOfertas(): void {
    this.eventSubscriber = this.eventManager.subscribe('historialOfertasListModification', () => this.reset());
  }

  delete(historialOfertas: IHistorialOfertas): void {
    const modalRef = this.modalService.open(HistorialOfertasDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.historialOfertas = historialOfertas;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateHistorialOfertas(data: IHistorialOfertas[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.historialOfertas.push(data[i]);
      }
    }
  }
}
