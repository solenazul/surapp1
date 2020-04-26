import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IModelo3D, Modelo3D } from 'app/shared/model/modelo-3-d.model';
import { Modelo3DService } from './modelo-3-d.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-modelo-3-d-update',
  templateUrl: './modelo-3-d-update.component.html'
})
export class Modelo3DUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  fechaDp: any;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    urt: [],
    colider: [],
    textureA: [],
    textureBC: [],
    textureN: [],
    textureR: [],
    shadow: [],
    acutalizado: [],
    fecha: [],
    idUser: []
  });

  constructor(
    protected modelo3DService: Modelo3DService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modelo3D }) => {
      this.updateForm(modelo3D);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(modelo3D: IModelo3D): void {
    this.editForm.patchValue({
      id: modelo3D.id,
      nombre: modelo3D.nombre,
      urt: modelo3D.urt,
      colider: modelo3D.colider,
      textureA: modelo3D.textureA,
      textureBC: modelo3D.textureBC,
      textureN: modelo3D.textureN,
      textureR: modelo3D.textureR,
      shadow: modelo3D.shadow,
      acutalizado: modelo3D.acutalizado,
      fecha: modelo3D.fecha,
      idUser: modelo3D.idUser
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const modelo3D = this.createFromForm();
    if (modelo3D.id !== undefined) {
      this.subscribeToSaveResponse(this.modelo3DService.update(modelo3D));
    } else {
      this.subscribeToSaveResponse(this.modelo3DService.create(modelo3D));
    }
  }

  private createFromForm(): IModelo3D {
    return {
      ...new Modelo3D(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      urt: this.editForm.get(['urt'])!.value,
      colider: this.editForm.get(['colider'])!.value,
      textureA: this.editForm.get(['textureA'])!.value,
      textureBC: this.editForm.get(['textureBC'])!.value,
      textureN: this.editForm.get(['textureN'])!.value,
      textureR: this.editForm.get(['textureR'])!.value,
      shadow: this.editForm.get(['shadow'])!.value,
      acutalizado: this.editForm.get(['acutalizado'])!.value,
      fecha: this.editForm.get(['fecha'])!.value,
      idUser: this.editForm.get(['idUser'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModelo3D>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
