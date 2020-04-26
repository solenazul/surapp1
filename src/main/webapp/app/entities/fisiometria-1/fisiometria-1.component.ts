import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFisiometria1 } from 'app/shared/model/fisiometria-1.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { Fisiometria1Service } from './fisiometria-1.service';
import { Fisiometria1DeleteDialogComponent } from './fisiometria-1-delete-dialog.component';

@Component({
  selector: 'jhi-fisiometria-1',
  templateUrl: './fisiometria-1.component.html'
})
export class Fisiometria1Component implements OnInit, OnDestroy {
  fisiometria1S: IFisiometria1[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected fisiometria1Service: Fisiometria1Service,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.fisiometria1S = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.fisiometria1Service
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IFisiometria1[]>) => this.paginateFisiometria1S(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.fisiometria1S = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFisiometria1S();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFisiometria1): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFisiometria1S(): void {
    this.eventSubscriber = this.eventManager.subscribe('fisiometria1ListModification', () => this.reset());
  }

  delete(fisiometria1: IFisiometria1): void {
    const modalRef = this.modalService.open(Fisiometria1DeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.fisiometria1 = fisiometria1;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateFisiometria1S(data: IFisiometria1[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.fisiometria1S.push(data[i]);
      }
    }
  }
}
