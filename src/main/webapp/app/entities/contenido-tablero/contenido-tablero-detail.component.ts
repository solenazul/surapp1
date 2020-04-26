import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContenidoTablero } from 'app/shared/model/contenido-tablero.model';

@Component({
  selector: 'jhi-contenido-tablero-detail',
  templateUrl: './contenido-tablero-detail.component.html'
})
export class ContenidoTableroDetailComponent implements OnInit {
  contenidoTablero: IContenidoTablero | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contenidoTablero }) => (this.contenidoTablero = contenidoTablero));
  }

  previousState(): void {
    window.history.back();
  }
}
