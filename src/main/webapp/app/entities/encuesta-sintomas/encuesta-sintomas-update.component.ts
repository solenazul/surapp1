import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEncuestaSintomas, EncuestaSintomas } from 'app/shared/model/encuesta-sintomas.model';
import { EncuestaSintomasService } from './encuesta-sintomas.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-encuesta-sintomas-update',
  templateUrl: './encuesta-sintomas-update.component.html'
})
export class EncuestaSintomasUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    fiebre: [],
    dolorGarganta: [],
    congestionNasal: [],
    tos: [],
    dificultadRespirar: [],
    fatiga: [],
    escalofrio: [],
    dolorMuscular: [],
    ninguno: [],
    fecha: [],
    user: []
  });

  constructor(
    protected encuestaSintomasService: EncuestaSintomasService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ encuestaSintomas }) => {
      if (!encuestaSintomas.id) {
        const today = moment().startOf('day');
        encuestaSintomas.fecha = today;
      }

      this.updateForm(encuestaSintomas);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(encuestaSintomas: IEncuestaSintomas): void {
    this.editForm.patchValue({
      id: encuestaSintomas.id,
      fiebre: encuestaSintomas.fiebre,
      dolorGarganta: encuestaSintomas.dolorGarganta,
      congestionNasal: encuestaSintomas.congestionNasal,
      tos: encuestaSintomas.tos,
      dificultadRespirar: encuestaSintomas.dificultadRespirar,
      fatiga: encuestaSintomas.fatiga,
      escalofrio: encuestaSintomas.escalofrio,
      dolorMuscular: encuestaSintomas.dolorMuscular,
      ninguno: encuestaSintomas.ninguno,
      fecha: encuestaSintomas.fecha ? encuestaSintomas.fecha.format(DATE_TIME_FORMAT) : null,
      user: encuestaSintomas.user
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const encuestaSintomas = this.createFromForm();
    if (encuestaSintomas.id !== undefined) {
      this.subscribeToSaveResponse(this.encuestaSintomasService.update(encuestaSintomas));
    } else {
      this.subscribeToSaveResponse(this.encuestaSintomasService.create(encuestaSintomas));
    }
  }

  private createFromForm(): IEncuestaSintomas {
    return {
      ...new EncuestaSintomas(),
      id: this.editForm.get(['id'])!.value,
      fiebre: this.editForm.get(['fiebre'])!.value,
      dolorGarganta: this.editForm.get(['dolorGarganta'])!.value,
      congestionNasal: this.editForm.get(['congestionNasal'])!.value,
      tos: this.editForm.get(['tos'])!.value,
      dificultadRespirar: this.editForm.get(['dificultadRespirar'])!.value,
      fatiga: this.editForm.get(['fatiga'])!.value,
      escalofrio: this.editForm.get(['escalofrio'])!.value,
      dolorMuscular: this.editForm.get(['dolorMuscular'])!.value,
      ninguno: this.editForm.get(['ninguno'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      user: this.editForm.get(['user'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEncuestaSintomas>>): void {
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
