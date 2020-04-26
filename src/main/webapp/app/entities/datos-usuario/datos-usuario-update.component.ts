import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IDatosUsuario, DatosUsuario } from 'app/shared/model/datos-usuario.model';
import { DatosUsuarioService } from './datos-usuario.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IPais } from 'app/shared/model/pais.model';
import { PaisService } from 'app/entities/pais/pais.service';

type SelectableEntity = IUser | IPais;

@Component({
  selector: 'jhi-datos-usuario-update',
  templateUrl: './datos-usuario-update.component.html'
})
export class DatosUsuarioUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  pais: IPais[] = [];
  fechaNacimientoDp: any;

  editForm = this.fb.group({
    id: [],
    fechaNacimiento: [],
    genero: [],
    telefono: [],
    pais: [],
    ciudad: [],
    direccion: [],
    image: [],
    imageContentType: [],
    idUser: [],
    nacionalidad: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected datosUsuarioService: DatosUsuarioService,
    protected userService: UserService,
    protected paisService: PaisService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ datosUsuario }) => {
      this.updateForm(datosUsuario);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.paisService.query().subscribe((res: HttpResponse<IPais[]>) => (this.pais = res.body || []));
    });
  }

  updateForm(datosUsuario: IDatosUsuario): void {
    this.editForm.patchValue({
      id: datosUsuario.id,
      fechaNacimiento: datosUsuario.fechaNacimiento,
      genero: datosUsuario.genero,
      telefono: datosUsuario.telefono,
      pais: datosUsuario.pais,
      ciudad: datosUsuario.ciudad,
      direccion: datosUsuario.direccion,
      image: datosUsuario.image,
      imageContentType: datosUsuario.imageContentType,
      idUser: datosUsuario.idUser,
      nacionalidad: datosUsuario.nacionalidad
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

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const datosUsuario = this.createFromForm();
    if (datosUsuario.id !== undefined) {
      this.subscribeToSaveResponse(this.datosUsuarioService.update(datosUsuario));
    } else {
      this.subscribeToSaveResponse(this.datosUsuarioService.create(datosUsuario));
    }
  }

  private createFromForm(): IDatosUsuario {
    return {
      ...new DatosUsuario(),
      id: this.editForm.get(['id'])!.value,
      fechaNacimiento: this.editForm.get(['fechaNacimiento'])!.value,
      genero: this.editForm.get(['genero'])!.value,
      telefono: this.editForm.get(['telefono'])!.value,
      pais: this.editForm.get(['pais'])!.value,
      ciudad: this.editForm.get(['ciudad'])!.value,
      direccion: this.editForm.get(['direccion'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      idUser: this.editForm.get(['idUser'])!.value,
      nacionalidad: this.editForm.get(['nacionalidad'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDatosUsuario>>): void {
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
