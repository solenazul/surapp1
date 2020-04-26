import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IProductos, Productos } from 'app/shared/model/productos.model';
import { ProductosService } from './productos.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-productos-update',
  templateUrl: './productos-update.component.html'
})
export class ProductosUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    descripcion: [],
    imagen: [],
    imagenContentType: [],
    inventario: [],
    tipo: [],
    impuesto: [],
    valor: [],
    unidad: [],
    estado: [],
    tiempoEntrega: [],
    dispinibilidad: [],
    nuevo: [],
    descuento: [],
    remate: [],
    tags: [],
    puntuacion: [],
    vistos: [],
    oferta: [],
    tiempoOferta: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected productosService: ProductosService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productos }) => {
      this.updateForm(productos);
    });
  }

  updateForm(productos: IProductos): void {
    this.editForm.patchValue({
      id: productos.id,
      nombre: productos.nombre,
      descripcion: productos.descripcion,
      imagen: productos.imagen,
      imagenContentType: productos.imagenContentType,
      inventario: productos.inventario,
      tipo: productos.tipo,
      impuesto: productos.impuesto,
      valor: productos.valor,
      unidad: productos.unidad,
      estado: productos.estado,
      tiempoEntrega: productos.tiempoEntrega,
      dispinibilidad: productos.dispinibilidad,
      nuevo: productos.nuevo,
      descuento: productos.descuento,
      remate: productos.remate,
      tags: productos.tags,
      puntuacion: productos.puntuacion,
      vistos: productos.vistos,
      oferta: productos.oferta,
      tiempoOferta: productos.tiempoOferta
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('surapp1App.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productos = this.createFromForm();
    if (productos.id !== undefined) {
      this.subscribeToSaveResponse(this.productosService.update(productos));
    } else {
      this.subscribeToSaveResponse(this.productosService.create(productos));
    }
  }

  private createFromForm(): IProductos {
    return {
      ...new Productos(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      imagenContentType: this.editForm.get(['imagenContentType'])!.value,
      imagen: this.editForm.get(['imagen'])!.value,
      inventario: this.editForm.get(['inventario'])!.value,
      tipo: this.editForm.get(['tipo'])!.value,
      impuesto: this.editForm.get(['impuesto'])!.value,
      valor: this.editForm.get(['valor'])!.value,
      unidad: this.editForm.get(['unidad'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      tiempoEntrega: this.editForm.get(['tiempoEntrega'])!.value,
      dispinibilidad: this.editForm.get(['dispinibilidad'])!.value,
      nuevo: this.editForm.get(['nuevo'])!.value,
      descuento: this.editForm.get(['descuento'])!.value,
      remate: this.editForm.get(['remate'])!.value,
      tags: this.editForm.get(['tags'])!.value,
      puntuacion: this.editForm.get(['puntuacion'])!.value,
      vistos: this.editForm.get(['vistos'])!.value,
      oferta: this.editForm.get(['oferta'])!.value,
      tiempoOferta: this.editForm.get(['tiempoOferta'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductos>>): void {
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
}
