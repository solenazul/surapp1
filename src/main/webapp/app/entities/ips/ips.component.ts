import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIPS } from 'app/shared/model/ips.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { IPSService } from './ips.service';
import { IPSDeleteDialogComponent } from './ips-delete-dialog.component';

@Component({
  selector: 'jhi-ips',
  templateUrl: './ips.component.html'
})
export class IPSComponent implements OnInit, OnDestroy {
  iPS: IIPS[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected iPSService: IPSService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.iPS = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.iPSService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IIPS[]>) => this.paginateIPS(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.iPS = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInIPS();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIPS): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIPS(): void {
    this.eventSubscriber = this.eventManager.subscribe('iPSListModification', () => this.reset());
  }

  delete(iPS: IIPS): void {
    const modalRef = this.modalService.open(IPSDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.iPS = iPS;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateIPS(data: IIPS[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.iPS.push(data[i]);
      }
    }
  }
}
