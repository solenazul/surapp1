import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFavoritosServicios } from 'app/shared/model/favoritos-servicios.model';

@Component({
  selector: 'jhi-favoritos-servicios-detail',
  templateUrl: './favoritos-servicios-detail.component.html'
})
export class FavoritosServiciosDetailComponent implements OnInit {
  favoritosServicios: IFavoritosServicios | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ favoritosServicios }) => (this.favoritosServicios = favoritosServicios));
  }

  previousState(): void {
    window.history.back();
  }
}
