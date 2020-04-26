import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFisiometria1, Fisiometria1 } from 'app/shared/model/fisiometria-1.model';
import { Fisiometria1Service } from './fisiometria-1.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-fisiometria-1-update',
  templateUrl: './fisiometria-1-update.component.html'
})
export class Fisiometria1UpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    ritmoCardiaco: [],
    ritmoRespiratorio: [],
    oximetria: [],
    presionArterialSistolica: [],
    presionArterialDiastolica: [],
    temperatura: [],
    timeInstant: [],
    user: []
  });

  constructor(
    protected fisiometria1Service: Fisiometria1Service,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fisiometria1 }) => {
      if (!fisiometria1.id) {
        const today = moment().startOf('day');
        fisiometria1.timeInstant = today;
      }

      this.updateForm(fisiometria1);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(fisiometria1: IFisiometria1): void {
    this.editForm.patchValue({
      id: fisiometria1.id,
      ritmoCardiaco: fisiometria1.ritmoCardiaco,
      ritmoRespiratorio: fisiometria1.ritmoRespiratorio,
      oximetria: fisiometria1.oximetria,
      presionArterialSistolica: fisiometria1.presionArterialSistolica,
      presionArterialDiastolica: fisiometria1.presionArterialDiastolica,
      temperatura: fisiometria1.temperatura,
      timeInstant: fisiometria1.timeInstant ? fisiometria1.timeInstant.format(DATE_TIME_FORMAT) : null,
      user: fisiometria1.user
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fisiometria1 = this.createFromForm();
    if (fisiometria1.id !== undefined) {
      this.subscribeToSaveResponse(this.fisiometria1Service.update(fisiometria1));
    } else {
      this.subscribeToSaveResponse(this.fisiometria1Service.create(fisiometria1));
    }
  }

  private createFromForm(): IFisiometria1 {
    return {
      ...new Fisiometria1(),
      id: this.editForm.get(['id'])!.value,
      ritmoCardiaco: this.editForm.get(['ritmoCardiaco'])!.value,
      ritmoRespiratorio: this.editForm.get(['ritmoRespiratorio'])!.value,
      oximetria: this.editForm.get(['oximetria'])!.value,
      presionArterialSistolica: this.editForm.get(['presionArterialSistolica'])!.value,
      presionArterialDiastolica: this.editForm.get(['presionArterialDiastolica'])!.value,
      temperatura: this.editForm.get(['temperatura'])!.value,
      timeInstant: this.editForm.get(['timeInstant'])!.value
        ? moment(this.editForm.get(['timeInstant'])!.value, DATE_TIME_FORMAT)
        : undefined,
      user: this.editForm.get(['user'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFisiometria1>>): void {
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
