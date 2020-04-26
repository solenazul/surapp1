import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IServicios } from 'app/shared/model/servicios.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ServiciosService } from './servicios.service';
import { ServiciosDeleteDialogComponent } from './servicios-delete-dialog.component';

@Component({
  selector: 'jhi-servicios',
  templateUrl: './servicios.component.html'
})
export class ServiciosComponent implements OnInit, OnDestroy {
  servicios: IServicios[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected serviciosService: ServiciosService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.servicios = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.serviciosService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IServicios[]>) => this.paginateServicios(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.servicios = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInServicios();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IServicios): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInServicios(): void {
    this.eventSubscriber = this.eventManager.subscribe('serviciosListModification', () => this.reset());
  }

  delete(servicios: IServicios): void {
    const modalRef = this.modalService.open(ServiciosDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.servicios = servicios;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateServicios(data: IServicios[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.servicios.push(data[i]);
      }
    }
  }
}
