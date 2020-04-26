import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICategoriaProducto, CategoriaProducto } from 'app/shared/model/categoria-producto.model';
import { CategoriaProductoService } from './categoria-producto.service';
import { ICategorias } from 'app/shared/model/categorias.model';
import { CategoriasService } from 'app/entities/categorias/categorias.service';
import { IProductos } from 'app/shared/model/productos.model';
import { ProductosService } from 'app/entities/productos/productos.service';

type SelectableEntity = ICategorias | IProductos;

@Component({
  selector: 'jhi-categoria-producto-update',
  templateUrl: './categoria-producto-update.component.html'
})
export class CategoriaProductoUpdateComponent implements OnInit {
  isSaving = false;
  categorias: ICategorias[] = [];
  productos: IProductos[] = [];

  editForm = this.fb.group({
    id: [],
    categoria: [],
    fecha: [],
    catagoriaId: [],
    productoId: []
  });

  constructor(
    protected categoriaProductoService: CategoriaProductoService,
    protected categoriasService: CategoriasService,
    protected productosService: ProductosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoriaProducto }) => {
      if (!categoriaProducto.id) {
        const today = moment().startOf('day');
        categoriaProducto.fecha = today;
      }

      this.updateForm(categoriaProducto);

      this.categoriasService.query().subscribe((res: HttpResponse<ICategorias[]>) => (this.categorias = res.body || []));

      this.productosService.query().subscribe((res: HttpResponse<IProductos[]>) => (this.productos = res.body || []));
    });
  }

  updateForm(categoriaProducto: ICategoriaProducto): void {
    this.editForm.patchValue({
      id: categoriaProducto.id,
      categoria: categoriaProducto.categoria,
      fecha: categoriaProducto.fecha ? categoriaProducto.fecha.format(DATE_TIME_FORMAT) : null,
      catagoriaId: categoriaProducto.catagoriaId,
      productoId: categoriaProducto.productoId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categoriaProducto = this.createFromForm();
    if (categoriaProducto.id !== undefined) {
      this.subscribeToSaveResponse(this.categoriaProductoService.update(categoriaProducto));
    } else {
      this.subscribeToSaveResponse(this.categoriaProductoService.create(categoriaProducto));
    }
  }

  private createFromForm(): ICategoriaProducto {
    return {
      ...new CategoriaProducto(),
      id: this.editForm.get(['id'])!.value,
      categoria: this.editForm.get(['categoria'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      catagoriaId: this.editForm.get(['catagoriaId'])!.value,
      productoId: this.editForm.get(['productoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoriaProducto>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
