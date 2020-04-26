import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICiudad } from 'app/shared/model/ciudad.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CiudadService } from './ciudad.service';
import { CiudadDeleteDialogComponent } from './ciudad-delete-dialog.component';

@Component({
  selector: 'jhi-ciudad',
  templateUrl: './ciudad.component.html'
})
export class CiudadComponent implements OnInit, OnDestroy {
  ciudads: ICiudad[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected ciudadService: CiudadService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.ciudads = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.ciudadService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICiudad[]>) => this.paginateCiudads(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.ciudads = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCiudads();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICiudad): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCiudads(): void {
    this.eventSubscriber = this.eventManager.subscribe('ciudadListModification', () => this.reset());
  }

  delete(ciudad: ICiudad): void {
    const modalRef = this.modalService.open(CiudadDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ciudad = ciudad;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCiudads(data: ICiudad[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.ciudads.push(data[i]);
      }
    }
  }
}
