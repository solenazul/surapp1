import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IComentariosServicios } from 'app/shared/model/comentarios-servicios.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ComentariosServiciosService } from './comentarios-servicios.service';
import { ComentariosServiciosDeleteDialogComponent } from './comentarios-servicios-delete-dialog.component';

@Component({
  selector: 'jhi-comentarios-servicios',
  templateUrl: './comentarios-servicios.component.html'
})
export class ComentariosServiciosComponent implements OnInit, OnDestroy {
  comentariosServicios: IComentariosServicios[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected comentariosServiciosService: ComentariosServiciosService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.comentariosServicios = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.comentariosServiciosService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IComentariosServicios[]>) => this.paginateComentariosServicios(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.comentariosServicios = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInComentariosServicios();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IComentariosServicios): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInComentariosServicios(): void {
    this.eventSubscriber = this.eventManager.subscribe('comentariosServiciosListModification', () => this.reset());
  }

  delete(comentariosServicios: IComentariosServicios): void {
    const modalRef = this.modalService.open(ComentariosServiciosDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.comentariosServicios = comentariosServicios;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateComentariosServicios(data: IComentariosServicios[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.comentariosServicios.push(data[i]);
      }
    }
  }
}
