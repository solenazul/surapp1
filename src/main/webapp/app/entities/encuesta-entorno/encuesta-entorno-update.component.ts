import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEncuestaEntorno, EncuestaEntorno } from 'app/shared/model/encuesta-entorno.model';
import { EncuestaEntornoService } from './encuesta-entorno.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-encuesta-entorno-update',
  templateUrl: './encuesta-entorno-update.component.html'
})
export class EncuestaEntornoUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    contactoSintomas: [],
    viajeInternacional: [],
    viajeNacional: [],
    trabajadorSalud: [],
    ninguna: [],
    fecha: [],
    user: []
  });

  constructor(
    protected encuestaEntornoService: EncuestaEntornoService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ encuestaEntorno }) => {
      if (!encuestaEntorno.id) {
        const today = moment().startOf('day');
        encuestaEntorno.fecha = today;
      }

      this.updateForm(encuestaEntorno);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(encuestaEntorno: IEncuestaEntorno): void {
    this.editForm.patchValue({
      id: encuestaEntorno.id,
      contactoSintomas: encuestaEntorno.contactoSintomas,
      viajeInternacional: encuestaEntorno.viajeInternacional,
      viajeNacional: encuestaEntorno.viajeNacional,
      trabajadorSalud: encuestaEntorno.trabajadorSalud,
      ninguna: encuestaEntorno.ninguna,
      fecha: encuestaEntorno.fecha ? encuestaEntorno.fecha.format(DATE_TIME_FORMAT) : null,
      user: encuestaEntorno.user
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const encuestaEntorno = this.createFromForm();
    if (encuestaEntorno.id !== undefined) {
      this.subscribeToSaveResponse(this.encuestaEntornoService.update(encuestaEntorno));
    } else {
      this.subscribeToSaveResponse(this.encuestaEntornoService.create(encuestaEntorno));
    }
  }

  private createFromForm(): IEncuestaEntorno {
    return {
      ...new EncuestaEntorno(),
      id: this.editForm.get(['id'])!.value,
      contactoSintomas: this.editForm.get(['contactoSintomas'])!.value,
      viajeInternacional: this.editForm.get(['viajeInternacional'])!.value,
      viajeNacional: this.editForm.get(['viajeNacional'])!.value,
      trabajadorSalud: this.editForm.get(['trabajadorSalud'])!.value,
      ninguna: this.editForm.get(['ninguna'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      user: this.editForm.get(['user'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEncuestaEntorno>>): void {
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
