import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAlarma, Alarma } from 'app/shared/model/alarma.model';
import { AlarmaService } from './alarma.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-alarma-update',
  templateUrl: './alarma-update.component.html'
})
export class AlarmaUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    timeInstant: [],
    descripcion: [],
    procedimiento: [],
    user: []
  });

  constructor(
    protected alarmaService: AlarmaService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ alarma }) => {
      if (!alarma.id) {
        const today = moment().startOf('day');
        alarma.timeInstant = today;
      }

      this.updateForm(alarma);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(alarma: IAlarma): void {
    this.editForm.patchValue({
      id: alarma.id,
      timeInstant: alarma.timeInstant ? alarma.timeInstant.format(DATE_TIME_FORMAT) : null,
      descripcion: alarma.descripcion,
      procedimiento: alarma.procedimiento,
      user: alarma.user
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const alarma = this.createFromForm();
    if (alarma.id !== undefined) {
      this.subscribeToSaveResponse(this.alarmaService.update(alarma));
    } else {
      this.subscribeToSaveResponse(this.alarmaService.create(alarma));
    }
  }

  private createFromForm(): IAlarma {
    return {
      ...new Alarma(),
      id: this.editForm.get(['id'])!.value,
      timeInstant: this.editForm.get(['timeInstant'])!.value
        ? moment(this.editForm.get(['timeInstant'])!.value, DATE_TIME_FORMAT)
        : undefined,
      descripcion: this.editForm.get(['descripcion'])!.value,
      procedimiento: this.editForm.get(['procedimiento'])!.value,
      user: this.editForm.get(['user'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlarma>>): void {
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
