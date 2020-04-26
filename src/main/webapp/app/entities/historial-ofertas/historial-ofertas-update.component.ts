import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IHistorialOfertas, HistorialOfertas } from 'app/shared/model/historial-ofertas.model';
import { HistorialOfertasService } from './historial-ofertas.service';
import { IProductos } from 'app/shared/model/productos.model';
import { ProductosService } from 'app/entities/productos/productos.service';

@Component({
  selector: 'jhi-historial-ofertas-update',
  templateUrl: './historial-ofertas-update.component.html'
})
export class HistorialOfertasUpdateComponent implements OnInit {
  isSaving = false;
  productos: IProductos[] = [];
  fechaInicialDp: any;
  fechaFinalDp: any;

  editForm = this.fb.group({
    id: [],
    valorProducto: [],
    valorOferta: [],
    fechaInicial: [],
    fechaFinal: [],
    productoId: []
  });

  constructor(
    protected historialOfertasService: HistorialOfertasService,
    protected productosService: ProductosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ historialOfertas }) => {
      this.updateForm(historialOfertas);

      this.productosService.query().subscribe((res: HttpResponse<IProductos[]>) => (this.productos = res.body || []));
    });
  }

  updateForm(historialOfertas: IHistorialOfertas): void {
    this.editForm.patchValue({
      id: historialOfertas.id,
      valorProducto: historialOfertas.valorProducto,
      valorOferta: historialOfertas.valorOferta,
      fechaInicial: historialOfertas.fechaInicial,
      fechaFinal: historialOfertas.fechaFinal,
      productoId: historialOfertas.productoId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const historialOfertas = this.createFromForm();
    if (historialOfertas.id !== undefined) {
      this.subscribeToSaveResponse(this.historialOfertasService.update(historialOfertas));
    } else {
      this.subscribeToSaveResponse(this.historialOfertasService.create(historialOfertas));
    }
  }

  private createFromForm(): IHistorialOfertas {
    return {
      ...new HistorialOfertas(),
      id: this.editForm.get(['id'])!.value,
      valorProducto: this.editForm.get(['valorProducto'])!.value,
      valorOferta: this.editForm.get(['valorOferta'])!.value,
      fechaInicial: this.editForm.get(['fechaInicial'])!.value,
      fechaFinal: this.editForm.get(['fechaFinal'])!.value,
      productoId: this.editForm.get(['productoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHistorialOfertas>>): void {
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
