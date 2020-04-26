import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategoriaTablero } from 'app/shared/model/categoria-tablero.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CategoriaTableroService } from './categoria-tablero.service';
import { CategoriaTableroDeleteDialogComponent } from './categoria-tablero-delete-dialog.component';

@Component({
  selector: 'jhi-categoria-tablero',
  templateUrl: './categoria-tablero.component.html'
})
export class CategoriaTableroComponent implements OnInit, OnDestroy {
  categoriaTableros: ICategoriaTablero[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected categoriaTableroService: CategoriaTableroService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.categoriaTableros = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.categoriaTableroService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICategoriaTablero[]>) => this.paginateCategoriaTableros(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.categoriaTableros = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCategoriaTableros();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICategoriaTablero): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCategoriaTableros(): void {
    this.eventSubscriber = this.eventManager.subscribe('categoriaTableroListModification', () => this.reset());
  }

  delete(categoriaTablero: ICategoriaTablero): void {
    const modalRef = this.modalService.open(CategoriaTableroDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.categoriaTablero = categoriaTablero;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCategoriaTableros(data: ICategoriaTablero[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.categoriaTableros.push(data[i]);
      }
    }
  }
}
