import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPaciente, Paciente } from 'app/shared/model/paciente.model';
import { PacienteService } from './paciente.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IIPS } from 'app/shared/model/ips.model';
import { IPSService } from 'app/entities/ips/ips.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IIPS | IUser;

@Component({
  selector: 'jhi-paciente-update',
  templateUrl: './paciente-update.component.html'
})
export class PacienteUpdateComponent implements OnInit {
  isSaving = false;
  ips: IIPS[] = [];
  users: IUser[] = [];
  fechaNacimientoDp: any;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    identificacion: [],
    edad: [],
    sexo: [],
    fechaNacimiento: [],
    condicion: [],
    ips: [],
    user: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected pacienteService: PacienteService,
    protected iPSService: IPSService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paciente }) => {
      this.updateForm(paciente);

      this.iPSService.query().subscribe((res: HttpResponse<IIPS[]>) => (this.ips = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(paciente: IPaciente): void {
    this.editForm.patchValue({
      id: paciente.id,
      nombre: paciente.nombre,
      identificacion: paciente.identificacion,
      edad: paciente.edad,
      sexo: paciente.sexo,
      fechaNacimiento: paciente.fechaNacimiento,
      condicion: paciente.condicion,
      ips: paciente.ips,
      user: paciente.user
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('surapp1App.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paciente = this.createFromForm();
    if (paciente.id !== undefined) {
      this.subscribeToSaveResponse(this.pacienteService.update(paciente));
    } else {
      this.subscribeToSaveResponse(this.pacienteService.create(paciente));
    }
  }

  private createFromForm(): IPaciente {
    return {
      ...new Paciente(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      identificacion: this.editForm.get(['identificacion'])!.value,
      edad: this.editForm.get(['edad'])!.value,
      sexo: this.editForm.get(['sexo'])!.value,
      fechaNacimiento: this.editForm.get(['fechaNacimiento'])!.value,
      condicion: this.editForm.get(['condicion'])!.value,
      ips: this.editForm.get(['ips'])!.value,
      user: this.editForm.get(['user'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaciente>>): void {
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
