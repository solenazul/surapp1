import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IComentariosServicios } from 'app/shared/model/comentarios-servicios.model';

@Component({
  selector: 'jhi-comentarios-servicios-detail',
  templateUrl: './comentarios-servicios-detail.component.html'
})
export class ComentariosServiciosDetailComponent implements OnInit {
  comentariosServicios: IComentariosServicios | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comentariosServicios }) => (this.comentariosServicios = comentariosServicios));
  }

  previousState(): void {
    window.history.back();
  }
}
