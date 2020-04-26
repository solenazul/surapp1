import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFavoritosProductos, FavoritosProductos } from 'app/shared/model/favoritos-productos.model';
import { FavoritosProductosService } from './favoritos-productos.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IProductos } from 'app/shared/model/productos.model';
import { ProductosService } from 'app/entities/productos/productos.service';

type SelectableEntity = IUser | IProductos;

@Component({
  selector: 'jhi-favoritos-productos-update',
  templateUrl: './favoritos-productos-update.component.html'
})
export class FavoritosProductosUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  productos: IProductos[] = [];
  fechaDp: any;

  editForm = this.fb.group({
    id: [],
    fecha: [],
    idUser: [],
    productoId: []
  });

  constructor(
    protected favoritosProductosService: FavoritosProductosService,
    protected userService: UserService,
    protected productosService: ProductosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ favoritosProductos }) => {
      this.updateForm(favoritosProductos);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.productosService.query().subscribe((res: HttpResponse<IProductos[]>) => (this.productos = res.body || []));
    });
  }

  updateForm(favoritosProductos: IFavoritosProductos): void {
    this.editForm.patchValue({
      id: favoritosProductos.id,
      fecha: favoritosProductos.fecha,
      idUser: favoritosProductos.idUser,
      productoId: favoritosProductos.productoId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const favoritosProductos = this.createFromForm();
    if (favoritosProductos.id !== undefined) {
      this.subscribeToSaveResponse(this.favoritosProductosService.update(favoritosProductos));
    } else {
      this.subscribeToSaveResponse(this.favoritosProductosService.create(favoritosProductos));
    }
  }

  private createFromForm(): IFavoritosProductos {
    return {
      ...new FavoritosProductos(),
      id: this.editForm.get(['id'])!.value,
      fecha: this.editForm.get(['fecha'])!.value,
      idUser: this.editForm.get(['idUser'])!.value,
      productoId: this.editForm.get(['productoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFavoritosProductos>>): void {
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
