import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IServicios, Servicios } from 'app/shared/model/servicios.model';
import { ServiciosService } from './servicios.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-servicios-update',
  templateUrl: './servicios-update.component.html'
})
export class ServiciosUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    descripcion: [],
    imagen: [],
    imagenContentType: [],
    videos: [],
    videosContentType: [],
    documento: [],
    documentoContentType: [],
    proveedor: [],
    impuesto: [],
    valor: [],
    unidad: [],
    dispinibilidad: [],
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
    protected serviciosService: ServiciosService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ servicios }) => {
      this.updateForm(servicios);
    });
  }

  updateForm(servicios: IServicios): void {
    this.editForm.patchValue({
      id: servicios.id,
      nombre: servicios.nombre,
      descripcion: servicios.descripcion,
      imagen: servicios.imagen,
      imagenContentType: servicios.imagenContentType,
      videos: servicios.videos,
      videosContentType: servicios.videosContentType,
      documento: servicios.documento,
      documentoContentType: servicios.documentoContentType,
      proveedor: servicios.proveedor,
      impuesto: servicios.impuesto,
      valor: servicios.valor,
      unidad: servicios.unidad,
      dispinibilidad: servicios.dispinibilidad,
      descuento: servicios.descuento,
      remate: servicios.remate,
      tags: servicios.tags,
      puntuacion: servicios.puntuacion,
      vistos: servicios.vistos,
      oferta: servicios.oferta,
      tiempoOferta: servicios.tiempoOferta
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
    const servicios = this.createFromForm();
    if (servicios.id !== undefined) {
      this.subscribeToSaveResponse(this.serviciosService.update(servicios));
    } else {
      this.subscribeToSaveResponse(this.serviciosService.create(servicios));
    }
  }

  private createFromForm(): IServicios {
    return {
      ...new Servicios(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      imagenContentType: this.editForm.get(['imagenContentType'])!.value,
      imagen: this.editForm.get(['imagen'])!.value,
      videosContentType: this.editForm.get(['videosContentType'])!.value,
      videos: this.editForm.get(['videos'])!.value,
      documentoContentType: this.editForm.get(['documentoContentType'])!.value,
      documento: this.editForm.get(['documento'])!.value,
      proveedor: this.editForm.get(['proveedor'])!.value,
      impuesto: this.editForm.get(['impuesto'])!.value,
      valor: this.editForm.get(['valor'])!.value,
      unidad: this.editForm.get(['unidad'])!.value,
      dispinibilidad: this.editForm.get(['dispinibilidad'])!.value,
      descuento: this.editForm.get(['descuento'])!.value,
      remate: this.editForm.get(['remate'])!.value,
      tags: this.editForm.get(['tags'])!.value,
      puntuacion: this.editForm.get(['puntuacion'])!.value,
      vistos: this.editForm.get(['vistos'])!.value,
      oferta: this.editForm.get(['oferta'])!.value,
      tiempoOferta: this.editForm.get(['tiempoOferta'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServicios>>): void {
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
