import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IComentariosProducto } from 'app/shared/model/comentarios-producto.model';

@Component({
  selector: 'jhi-comentarios-producto-detail',
  templateUrl: './comentarios-producto-detail.component.html'
})
export class ComentariosProductoDetailComponent implements OnInit {
  comentariosProducto: IComentariosProducto | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comentariosProducto }) => (this.comentariosProducto = comentariosProducto));
  }

  previousState(): void {
    window.history.back();
  }
}
