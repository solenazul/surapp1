import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEncuestaSintomas } from 'app/shared/model/encuesta-sintomas.model';

@Component({
  selector: 'jhi-encuesta-sintomas-detail',
  templateUrl: './encuesta-sintomas-detail.component.html'
})
export class EncuestaSintomasDetailComponent implements OnInit {
  encuestaSintomas: IEncuestaSintomas | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ encuestaSintomas }) => (this.encuestaSintomas = encuestaSintomas));
  }

  previousState(): void {
    window.history.back();
  }
}
