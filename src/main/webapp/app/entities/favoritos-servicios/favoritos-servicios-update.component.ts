import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFavoritosServicios, FavoritosServicios } from 'app/shared/model/favoritos-servicios.model';
import { FavoritosServiciosService } from './favoritos-servicios.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IServicios } from 'app/shared/model/servicios.model';
import { ServiciosService } from 'app/entities/servicios/servicios.service';

type SelectableEntity = IUser | IServicios;

@Component({
  selector: 'jhi-favoritos-servicios-update',
  templateUrl: './favoritos-servicios-update.component.html'
})
export class FavoritosServiciosUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  servicios: IServicios[] = [];
  fechaDp: any;

  editForm = this.fb.group({
    id: [],
    fecha: [],
    idUser: [],
    productoId: []
  });

  constructor(
    protected favoritosServiciosService: FavoritosServiciosService,
    protected userService: UserService,
    protected serviciosService: ServiciosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ favoritosServicios }) => {
      this.updateForm(favoritosServicios);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.serviciosService.query().subscribe((res: HttpResponse<IServicios[]>) => (this.servicios = res.body || []));
    });
  }

  updateForm(favoritosServicios: IFavoritosServicios): void {
    this.editForm.patchValue({
      id: favoritosServicios.id,
      fecha: favoritosServicios.fecha,
      idUser: favoritosServicios.idUser,
      productoId: favoritosServicios.productoId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const favoritosServicios = this.createFromForm();
    if (favoritosServicios.id !== undefined) {
      this.subscribeToSaveResponse(this.favoritosServiciosService.update(favoritosServicios));
    } else {
      this.subscribeToSaveResponse(this.favoritosServiciosService.create(favoritosServicios));
    }
  }

  private createFromForm(): IFavoritosServicios {
    return {
      ...new FavoritosServicios(),
      id: this.editForm.get(['id'])!.value,
      fecha: this.editForm.get(['fecha'])!.value,
      idUser: this.editForm.get(['idUser'])!.value,
      productoId: this.editForm.get(['productoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFavoritosServicios>>): void {
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
