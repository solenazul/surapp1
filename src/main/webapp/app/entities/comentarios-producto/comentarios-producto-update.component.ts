import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IComentariosProducto, ComentariosProducto } from 'app/shared/model/comentarios-producto.model';
import { ComentariosProductoService } from './comentarios-producto.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IProductos } from 'app/shared/model/productos.model';
import { ProductosService } from 'app/entities/productos/productos.service';

type SelectableEntity = IUser | IProductos;

@Component({
  selector: 'jhi-comentarios-producto-update',
  templateUrl: './comentarios-producto-update.component.html'
})
export class ComentariosProductoUpdateComponent implements OnInit {
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
    protected comentariosProductoService: ComentariosProductoService,
    protected userService: UserService,
    protected productosService: ProductosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comentariosProducto }) => {
      if (!comentariosProducto.id) {
        const today = moment().startOf('day');
        comentariosProducto.fecha = today;
      }

      this.updateForm(comentariosProducto);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.productosService.query().subscribe((res: HttpResponse<IProductos[]>) => (this.productos = res.body || []));
    });
  }

  updateForm(comentariosProducto: IComentariosProducto): void {
    this.editForm.patchValue({
      id: comentariosProducto.id,
      comentario: comentariosProducto.comentario,
      fecha: comentariosProducto.fecha ? comentariosProducto.fecha.format(DATE_TIME_FORMAT) : null,
      estado: comentariosProducto.estado,
      idUser: comentariosProducto.idUser,
      productoId: comentariosProducto.productoId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const comentariosProducto = this.createFromForm();
    if (comentariosProducto.id !== undefined) {
      this.subscribeToSaveResponse(this.comentariosProductoService.update(comentariosProducto));
    } else {
      this.subscribeToSaveResponse(this.comentariosProductoService.create(comentariosProducto));
    }
  }

  private createFromForm(): IComentariosProducto {
    return {
      ...new ComentariosProducto(),
      id: this.editForm.get(['id'])!.value,
      comentario: this.editForm.get(['comentario'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      estado: this.editForm.get(['estado'])!.value,
      idUser: this.editForm.get(['idUser'])!.value,
      productoId: this.editForm.get(['productoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComentariosProducto>>): void {
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
