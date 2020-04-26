import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAlarma } from 'app/shared/model/alarma.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AlarmaService } from './alarma.service';
import { AlarmaDeleteDialogComponent } from './alarma-delete-dialog.component';

@Component({
  selector: 'jhi-alarma',
  templateUrl: './alarma.component.html'
})
export class AlarmaComponent implements OnInit, OnDestroy {
  alarmas: IAlarma[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected alarmaService: AlarmaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.alarmas = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.alarmaService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IAlarma[]>) => this.paginateAlarmas(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.alarmas = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAlarmas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAlarma): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAlarmas(): void {
    this.eventSubscriber = this.eventManager.subscribe('alarmaListModification', () => this.reset());
  }

  delete(alarma: IAlarma): void {
    const modalRef = this.modalService.open(AlarmaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.alarma = alarma;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAlarmas(data: IAlarma[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.alarmas.push(data[i]);
      }
    }
  }
}
