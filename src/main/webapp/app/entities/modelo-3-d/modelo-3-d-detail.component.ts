import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IModelo3D } from 'app/shared/model/modelo-3-d.model';

@Component({
  selector: 'jhi-modelo-3-d-detail',
  templateUrl: './modelo-3-d-detail.component.html'
})
export class Modelo3DDetailComponent implements OnInit {
  modelo3D: IModelo3D | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modelo3D }) => (this.modelo3D = modelo3D));
  }

  previousState(): void {
    window.history.back();
  }
}
