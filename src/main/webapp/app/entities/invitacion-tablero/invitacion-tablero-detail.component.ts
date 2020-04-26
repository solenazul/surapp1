import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInvitacionTablero } from 'app/shared/model/invitacion-tablero.model';

@Component({
  selector: 'jhi-invitacion-tablero-detail',
  templateUrl: './invitacion-tablero-detail.component.html'
})
export class InvitacionTableroDetailComponent implements OnInit {
  invitacionTablero: IInvitacionTablero | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invitacionTablero }) => (this.invitacionTablero = invitacionTablero));
  }

  previousState(): void {
    window.history.back();
  }
}
