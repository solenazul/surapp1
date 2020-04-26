import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITableros } from 'app/shared/model/tableros.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TablerosService } from './tableros.service';
import { TablerosDeleteDialogComponent } from './tableros-delete-dialog.component';

@Component({
  selector: 'jhi-tableros',
  templateUrl: './tableros.component.html'
})
export class TablerosComponent implements OnInit, OnDestroy {
  tableros: ITableros[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected tablerosService: TablerosService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.tableros = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.tablerosService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITableros[]>) => this.paginateTableros(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.tableros = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTableros();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITableros): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTableros(): void {
    this.eventSubscriber = this.eventManager.subscribe('tablerosListModification', () => this.reset());
  }

  delete(tableros: ITableros): void {
    const modalRef = this.modalService.open(TablerosDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tableros = tableros;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTableros(data: ITableros[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.tableros.push(data[i]);
      }
    }
  }
}
