import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFavoritosProductos } from 'app/shared/model/favoritos-productos.model';

@Component({
  selector: 'jhi-favoritos-productos-detail',
  templateUrl: './favoritos-productos-detail.component.html'
})
export class FavoritosProductosDetailComponent implements OnInit {
  favoritosProductos: IFavoritosProductos | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ favoritosProductos }) => (this.favoritosProductos = favoritosProductos));
  }

  previousState(): void {
    window.history.back();
  }
}
