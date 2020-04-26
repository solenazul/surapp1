import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategoriaTablero } from 'app/shared/model/categoria-tablero.model';

@Component({
  selector: 'jhi-categoria-tablero-detail',
  templateUrl: './categoria-tablero-detail.component.html'
})
export class CategoriaTableroDetailComponent implements OnInit {
  categoriaTablero: ICategoriaTablero | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoriaTablero }) => (this.categoriaTablero = categoriaTablero));
  }

  previousState(): void {
    window.history.back();
  }
}
