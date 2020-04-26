import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEncuestaEntorno } from 'app/shared/model/encuesta-entorno.model';

@Component({
  selector: 'jhi-encuesta-entorno-detail',
  templateUrl: './encuesta-entorno-detail.component.html'
})
export class EncuestaEntornoDetailComponent implements OnInit {
  encuestaEntorno: IEncuestaEntorno | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ encuestaEntorno }) => (this.encuestaEntorno = encuestaEntorno));
  }

  previousState(): void {
    window.history.back();
  }
}
