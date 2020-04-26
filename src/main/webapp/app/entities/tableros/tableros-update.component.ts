import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITableros, Tableros } from 'app/shared/model/tableros.model';
import { TablerosService } from './tableros.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ICategoriaTablero } from 'app/shared/model/categoria-tablero.model';
import { CategoriaTableroService } from 'app/entities/categoria-tablero/categoria-tablero.service';

type SelectableEntity = IUser | ICategoriaTablero;

@Component({
  selector: 'jhi-tableros-update',
  templateUrl: './tableros-update.component.html'
})
export class TablerosUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  categoriatableros: ICategoriaTablero[] = [];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    fecha: [],
    privado: [],
    calificacion: [],
    idUser: [],
    categoriaId: []
  });

  constructor(
    protected tablerosService: TablerosService,
    protected userService: UserService,
    protected categoriaTableroService: CategoriaTableroService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tableros }) => {
      if (!tableros.id) {
        const today = moment().startOf('day');
        tableros.fecha = today;
      }

      this.updateForm(tableros);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.categoriaTableroService.query().subscribe((res: HttpResponse<ICategoriaTablero[]>) => (this.categoriatableros = res.body || []));
    });
  }

  updateForm(tableros: ITableros): void {
    this.editForm.patchValue({
      id: tableros.id,
      nombre: tableros.nombre,
      fecha: tableros.fecha ? tableros.fecha.format(DATE_TIME_FORMAT) : null,
      privado: tableros.privado,
      calificacion: tableros.calificacion,
      idUser: tableros.idUser,
      categoriaId: tableros.categoriaId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tableros = this.createFromForm();
    if (tableros.id !== undefined) {
      this.subscribeToSaveResponse(this.tablerosService.update(tableros));
    } else {
      this.subscribeToSaveResponse(this.tablerosService.create(tableros));
    }
  }

  private createFromForm(): ITableros {
    return {
      ...new Tableros(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? moment(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      privado: this.editForm.get(['privado'])!.value,
      calificacion: this.editForm.get(['calificacion'])!.value,
      idUser: this.editForm.get(['idUser'])!.value,
      categoriaId: this.editForm.get(['categoriaId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITableros>>): void {
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
