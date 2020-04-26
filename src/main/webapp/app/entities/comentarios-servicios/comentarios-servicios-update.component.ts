import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IComentariosServicios, ComentariosServicios } from 'app/shared/model/comentarios-servicios.model';
import { ComentariosServiciosService } from './comentarios-servicios.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IProductos } from 'app/shared/model/productos.model';
import { ProductosService } from 'app/entities/productos/productos.service';

type SelectableEntity = IUser | IProductos;

@Component({
  selector: 'jhi-comentarios-servicios-update',
  templateUrl: './comentarios-servicios-update.component.html'
})
export class ComentariosServiciosUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  productos: IProductos[] = [];

  editForm = this.fb.group({
    id: [],
    comentario: [],
    fecha: [],
    estado: [],
    idUser: [],
    productoId: []
  });

  constructor(
    protected comentariosServiciosService: ComentariosServiciosService,
    protected userService: UserService,
    protected productosService: ProductosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comentariosServicios }) => {
      if (!comentariosServicios.id) {
        const today = moment().startOf('day');
        comentariosServicios.fecha = today;
      }

      this.updateForm(comentariosServicios);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.productosService.query().subscribe((res: HttpResponse<IProductos[]>) => (this.productos = res.body || []));
    });
  }

  updateForm(comentariosServicios: IComentariosServicios): void {
    this.editForm.patchValue({
      id: comentariosServicios.id,
      comentario: comentariosServicios.comentario,
      fecha: comentariosServicios.fecha ? comentariosServicios.fecha.format(DATE_TIME_FORMAT) : null,
      estado: comentariosServicios.estado,
      idUser: comentariosServicios.idUser,
      productoId: comentariosServicios.productoId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const comentariosServicios = this.createFromForm();
    if (comentariosServicios.id !== undefined) {
      this.subscribeToSaveResponse(this.comentariosServiciosService.update(comentariosServicios));
    } else {
      this.subscribeToSaveResponse(this.comentariosServiciosService.create(comentariosServicios));
    }
  }

  private createFromForm(): IComentariosServicios {
    return {
      ...new ComentariosServicios(),
      id: this.editForm.get(['id'])!.value,
      comentario: this.editForm.get(['comentario'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      estado: this.editForm.get(['estado'])!.value,
      idUser: this.editForm.get(['idUser'])!.value,
      productoId: this.editForm.get(['productoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComentariosServicios>>): void {
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
