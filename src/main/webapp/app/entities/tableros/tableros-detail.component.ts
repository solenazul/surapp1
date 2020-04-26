import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITableros } from 'app/shared/model/tableros.model';

@Component({
  selector: 'jhi-tableros-detail',
  templateUrl: './tableros-detail.component.html'
})
export class TablerosDetailComponent implements OnInit {
  tableros: ITableros | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tableros }) => (this.tableros = tableros));
  }

  previousState(): void {
    window.history.back();
  }
}
