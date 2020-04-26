import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IModelo3D } from 'app/shared/model/modelo-3-d.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { Modelo3DService } from './modelo-3-d.service';
import { Modelo3DDeleteDialogComponent } from './modelo-3-d-delete-dialog.component';

@Component({
  selector: 'jhi-modelo-3-d',
  templateUrl: './modelo-3-d.component.html'
})
export class Modelo3DComponent implements OnInit, OnDestroy {
  modelo3DS: IModelo3D[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected modelo3DService: Modelo3DService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.modelo3DS = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.modelo3DService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IModelo3D[]>) => this.paginateModelo3DS(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.modelo3DS = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInModelo3DS();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IModelo3D): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInModelo3DS(): void {
    this.eventSubscriber = this.eventManager.subscribe('modelo3DListModification', () => this.reset());
  }

  delete(modelo3D: IModelo3D): void {
    const modalRef = this.modalService.open(Modelo3DDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.modelo3D = modelo3D;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateModelo3DS(data: IModelo3D[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.modelo3DS.push(data[i]);
      }
    }
  }
}
