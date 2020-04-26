import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IComentarioBlog } from 'app/shared/model/comentario-blog.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ComentarioBlogService } from './comentario-blog.service';
import { ComentarioBlogDeleteDialogComponent } from './comentario-blog-delete-dialog.component';

@Component({
  selector: 'jhi-comentario-blog',
  templateUrl: './comentario-blog.component.html'
})
export class ComentarioBlogComponent implements OnInit, OnDestroy {
  comentarioBlogs: IComentarioBlog[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected comentarioBlogService: ComentarioBlogService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.comentarioBlogs = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.comentarioBlogService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IComentarioBlog[]>) => this.paginateComentarioBlogs(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.comentarioBlogs = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInComentarioBlogs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IComentarioBlog): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInComentarioBlogs(): void {
    this.eventSubscriber = this.eventManager.subscribe('comentarioBlogListModification', () => this.reset());
  }

  delete(comentarioBlog: IComentarioBlog): void {
    const modalRef = this.modalService.open(ComentarioBlogDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.comentarioBlog = comentarioBlog;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateComentarioBlogs(data: IComentarioBlog[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.comentarioBlogs.push(data[i]);
      }
    }
  }
}
