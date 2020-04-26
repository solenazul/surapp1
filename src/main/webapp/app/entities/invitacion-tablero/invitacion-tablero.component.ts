import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInvitacionTablero } from 'app/shared/model/invitacion-tablero.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { InvitacionTableroService } from './invitacion-tablero.service';
import { InvitacionTableroDeleteDialogComponent } from './invitacion-tablero-delete-dialog.component';

@Component({
  selector: 'jhi-invitacion-tablero',
  templateUrl: './invitacion-tablero.component.html'
})
export class InvitacionTableroComponent implements OnInit, OnDestroy {
  invitacionTableros: IInvitacionTablero[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected invitacionTableroService: InvitacionTableroService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.invitacionTableros = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.invitacionTableroService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IInvitacionTablero[]>) => this.paginateInvitacionTableros(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.invitacionTableros = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInvitacionTableros();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInvitacionTablero): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInvitacionTableros(): void {
    this.eventSubscriber = this.eventManager.subscribe('invitacionTableroListModification', () => this.reset());
  }

  delete(invitacionTablero: IInvitacionTablero): void {
    const modalRef = this.modalService.open(InvitacionTableroDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.invitacionTablero = invitacionTablero;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateInvitacionTableros(data: IInvitacionTablero[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.invitacionTableros.push(data[i]);
      }
    }
  }
}
