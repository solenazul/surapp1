import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICategoriaTablero, CategoriaTablero } from 'app/shared/model/categoria-tablero.model';
import { CategoriaTableroService } from './categoria-tablero.service';
import { IProductos } from 'app/shared/model/productos.model';
import { ProductosService } from 'app/entities/productos/productos.service';

@Component({
  selector: 'jhi-categoria-tablero-update',
  templateUrl: './categoria-tablero-update.component.html'
})
export class CategoriaTableroUpdateComponent implements OnInit {
  isSaving = false;
  productos: IProductos[] = [];

  editForm = this.fb.group({
    id: [],
    categoria: [],
    fecha: [],
    productoId: []
  });

  constructor(
    protected categoriaTableroService: CategoriaTableroService,
    protected productosService: ProductosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoriaTablero }) => {
      if (!categoriaTablero.id) {
        const today = moment().startOf('day');
        categoriaTablero.fecha = today;
      }

      this.updateForm(categoriaTablero);

      this.productosService.query().subscribe((res: HttpResponse<IProductos[]>) => (this.productos = res.body || []));
    });
  }

  updateForm(categoriaTablero: ICategoriaTablero): void {
    this.editForm.patchValue({
      id: categoriaTablero.id,
      categoria: categoriaTablero.categoria,
      fecha: categoriaTablero.fecha ? categoriaTablero.fecha.format(DATE_TIME_FORMAT) : null,
      productoId: categoriaTablero.productoId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categoriaTablero = this.createFromForm();
    if (categoriaTablero.id !== undefined) {
      this.subscribeToSaveResponse(this.categoriaTableroService.update(categoriaTablero));
    } else {
      this.subscribeToSaveResponse(this.categoriaTableroService.create(categoriaTablero));
    }
  }

  private createFromForm(): ICategoriaTablero {
    return {
      ...new CategoriaTablero(),
      id: this.editForm.get(['id'])!.value,
      categoria: this.editForm.get(['categoria'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      productoId: this.editForm.get(['productoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoriaTablero>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IProductos): any {
    return item.id;
  }
}
