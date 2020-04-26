import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEncuestaEntorno } from 'app/shared/model/encuesta-entorno.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EncuestaEntornoService } from './encuesta-entorno.service';
import { EncuestaEntornoDeleteDialogComponent } from './encuesta-entorno-delete-dialog.component';

@Component({
  selector: 'jhi-encuesta-entorno',
  templateUrl: './encuesta-entorno.component.html'
})
export class EncuestaEntornoComponent implements OnInit, OnDestroy {
  encuestaEntornos: IEncuestaEntorno[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected encuestaEntornoService: EncuestaEntornoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.encuestaEntornos = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.encuestaEntornoService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IEncuestaEntorno[]>) => this.paginateEncuestaEntornos(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.encuestaEntornos = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEncuestaEntornos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEncuestaEntorno): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEncuestaEntornos(): void {
    this.eventSubscriber = this.eventManager.subscribe('encuestaEntornoListModification', () => this.reset());
  }

  delete(encuestaEntorno: IEncuestaEntorno): void {
    const modalRef = this.modalService.open(EncuestaEntornoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.encuestaEntorno = encuestaEntorno;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateEncuestaEntornos(data: IEncuestaEntorno[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.encuestaEntornos.push(data[i]);
      }
    }
  }
}
