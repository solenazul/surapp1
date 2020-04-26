import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContenidoTablero } from 'app/shared/model/contenido-tablero.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ContenidoTableroService } from './contenido-tablero.service';
import { ContenidoTableroDeleteDialogComponent } from './contenido-tablero-delete-dialog.component';

@Component({
  selector: 'jhi-contenido-tablero',
  templateUrl: './contenido-tablero.component.html'
})
export class ContenidoTableroComponent implements OnInit, OnDestroy {
  contenidoTableros: IContenidoTablero[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected contenidoTableroService: ContenidoTableroService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.contenidoTableros = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.contenidoTableroService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IContenidoTablero[]>) => this.paginateContenidoTableros(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.contenidoTableros = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInContenidoTableros();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IContenidoTablero): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInContenidoTableros(): void {
    this.eventSubscriber = this.eventManager.subscribe('contenidoTableroListModification', () => this.reset());
  }

  delete(contenidoTablero: IContenidoTablero): void {
    const modalRef = this.modalService.open(ContenidoTableroDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contenidoTablero = contenidoTablero;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateContenidoTableros(data: IContenidoTablero[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.contenidoTableros.push(data[i]);
      }
    }
  }
}
