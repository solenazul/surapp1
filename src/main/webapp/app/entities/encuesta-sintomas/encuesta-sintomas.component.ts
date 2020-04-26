import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEncuestaSintomas } from 'app/shared/model/encuesta-sintomas.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EncuestaSintomasService } from './encuesta-sintomas.service';
import { EncuestaSintomasDeleteDialogComponent } from './encuesta-sintomas-delete-dialog.component';

@Component({
  selector: 'jhi-encuesta-sintomas',
  templateUrl: './encuesta-sintomas.component.html'
})
export class EncuestaSintomasComponent implements OnInit, OnDestroy {
  encuestaSintomas: IEncuestaSintomas[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected encuestaSintomasService: EncuestaSintomasService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.encuestaSintomas = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.encuestaSintomasService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IEncuestaSintomas[]>) => this.paginateEncuestaSintomas(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.encuestaSintomas = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEncuestaSintomas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEncuestaSintomas): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEncuestaSintomas(): void {
    this.eventSubscriber = this.eventManager.subscribe('encuestaSintomasListModification', () => this.reset());
  }

  delete(encuestaSintomas: IEncuestaSintomas): void {
    const modalRef = this.modalService.open(EncuestaSintomasDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.encuestaSintomas = encuestaSintomas;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateEncuestaSintomas(data: IEncuestaSintomas[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.encuestaSintomas.push(data[i]);
      }
    }
  }
}
