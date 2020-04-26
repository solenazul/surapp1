import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IInvitacionTablero, InvitacionTablero } from 'app/shared/model/invitacion-tablero.model';
import { InvitacionTableroService } from './invitacion-tablero.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ITableros } from 'app/shared/model/tableros.model';
import { TablerosService } from 'app/entities/tableros/tableros.service';

type SelectableEntity = IUser | ITableros;

@Component({
  selector: 'jhi-invitacion-tablero-update',
  templateUrl: './invitacion-tablero-update.component.html'
})
export class InvitacionTableroUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  tableros: ITableros[] = [];

  editForm = this.fb.group({
    id: [],
    estado: [],
    fecha: [],
    idUser: [],
    tableroId: []
  });

  constructor(
    protected invitacionTableroService: InvitacionTableroService,
    protected userService: UserService,
    protected tablerosService: TablerosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invitacionTablero }) => {
      if (!invitacionTablero.id) {
        const today = moment().startOf('day');
        invitacionTablero.fecha = today;
      }

      this.updateForm(invitacionTablero);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.tablerosService.query().subscribe((res: HttpResponse<ITableros[]>) => (this.tableros = res.body || []));
    });
  }

  updateForm(invitacionTablero: IInvitacionTablero): void {
    this.editForm.patchValue({
      id: invitacionTablero.id,
      estado: invitacionTablero.estado,
      fecha: invitacionTablero.fecha ? invitacionTablero.fecha.format(DATE_TIME_FORMAT) : null,
      idUser: invitacionTablero.idUser,
      tableroId: invitacionTablero.tableroId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const invitacionTablero = this.createFromForm();
    if (invitacionTablero.id !== undefined) {
      this.subscribeToSaveResponse(this.invitacionTableroService.update(invitacionTablero));
    } else {
      this.subscribeToSaveResponse(this.invitacionTableroService.create(invitacionTablero));
    }
  }

  private createFromForm(): IInvitacionTablero {
    return {
      ...new InvitacionTablero(),
      id: this.editForm.get(['id'])!.value,
      estado: this.editForm.get(['estado'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      idUser: this.editForm.get(['idUser'])!.value,
      tableroId: this.editForm.get(['tableroId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvitacionTablero>>): void {
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
