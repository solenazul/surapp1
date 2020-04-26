import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategorias } from 'app/shared/model/categorias.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CategoriasService } from './categorias.service';
import { CategoriasDeleteDialogComponent } from './categorias-delete-dialog.component';

@Component({
  selector: 'jhi-categorias',
  templateUrl: './categorias.component.html'
})
export class CategoriasComponent implements OnInit, OnDestroy {
  categorias: ICategorias[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected categoriasService: CategoriasService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.categorias = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.categoriasService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICategorias[]>) => this.paginateCategorias(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.categorias = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCategorias();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICategorias): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCategorias(): void {
    this.eventSubscriber = this.eventManager.subscribe('categoriasListModification', () => this.reset());
  }

  delete(categorias: ICategorias): void {
    const modalRef = this.modalService.open(CategoriasDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.categorias = categorias;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCategorias(data: ICategorias[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.categorias.push(data[i]);
      }
    }
  }
}
