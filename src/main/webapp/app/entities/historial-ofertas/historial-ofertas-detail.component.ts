import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHistorialOfertas } from 'app/shared/model/historial-ofertas.model';

@Component({
  selector: 'jhi-historial-ofertas-detail',
  templateUrl: './historial-ofertas-detail.component.html'
})
export class HistorialOfertasDetailComponent implements OnInit {
  historialOfertas: IHistorialOfertas | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ historialOfertas }) => (this.historialOfertas = historialOfertas));
  }

  previousState(): void {
    window.history.back();
  }
}
