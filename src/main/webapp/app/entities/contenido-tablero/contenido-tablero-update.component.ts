import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IContenidoTablero, ContenidoTablero } from 'app/shared/model/contenido-tablero.model';
import { ContenidoTableroService } from './contenido-tablero.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ITableros } from 'app/shared/model/tableros.model';
import { TablerosService } from 'app/entities/tableros/tableros.service';
import { IProductos } from 'app/shared/model/productos.model';
import { ProductosService } from 'app/entities/productos/productos.service';

type SelectableEntity = IUser | ITableros | IProductos;

@Component({
  selector: 'jhi-contenido-tablero-update',
  templateUrl: './contenido-tablero-update.component.html'
})
export class ContenidoTableroUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  tableros: ITableros[] = [];
  productos: IProductos[] = [];

  editForm = this.fb.group({
    id: [],
    comentario: [],
    fecha: [],
    idUser: [],
    tableroId: [],
    productoId: []
  });

  constructor(
    protected contenidoTableroService: ContenidoTableroService,
    protected userService: UserService,
    protected tablerosService: TablerosService,
    protected productosService: ProductosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contenidoTablero }) => {
      if (!contenidoTablero.id) {
        const today = moment().startOf('day');
        contenidoTablero.fecha = today;
      }

      this.updateForm(contenidoTablero);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.tablerosService.query().subscribe((res: HttpResponse<ITableros[]>) => (this.tableros = res.body || []));

      this.productosService.query().subscribe((res: HttpResponse<IProductos[]>) => (this.productos = res.body || []));
    });
  }

  updateForm(contenidoTablero: IContenidoTablero): void {
    this.editForm.patchValue({
      id: contenidoTablero.id,
      comentario: contenidoTablero.comentario,
      fecha: contenidoTablero.fecha ? contenidoTablero.fecha.format(DATE_TIME_FORMAT) : null,
      idUser: contenidoTablero.idUser,
      tableroId: contenidoTablero.tableroId,
      productoId: contenidoTablero.productoId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contenidoTablero = this.createFromForm();
    if (contenidoTablero.id !== undefined) {
      this.subscribeToSaveResponse(this.contenidoTableroService.update(contenidoTablero));
    } else {
      this.subscribeToSaveResponse(this.contenidoTableroService.create(contenidoTablero));
    }
  }

  private createFromForm(): IContenidoTablero {
    return {
      ...new ContenidoTablero(),
      id: this.editForm.get(['id'])!.value,
      comentario: this.editForm.get(['comentario'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      idUser: this.editForm.get(['idUser'])!.value,
      tableroId: this.editForm.get(['tableroId'])!.value,
      productoId: this.editForm.get(['productoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContenidoTablero>>): void {
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
