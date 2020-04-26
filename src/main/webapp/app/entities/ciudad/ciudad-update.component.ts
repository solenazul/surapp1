import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICiudad, Ciudad } from 'app/shared/model/ciudad.model';
import { CiudadService } from './ciudad.service';
import { IPais } from 'app/shared/model/pais.model';
import { PaisService } from 'app/entities/pais/pais.service';

@Component({
  selector: 'jhi-ciudad-update',
  templateUrl: './ciudad-update.component.html'
})
export class CiudadUpdateComponent implements OnInit {
  isSaving = false;
  pais: IPais[] = [];

  editForm = this.fb.group({
    id: [],
    nombreCiudad: [null, [Validators.required]],
    pais: []
  });

  constructor(
    protected ciudadService: CiudadService,
    protected paisService: PaisService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ciudad }) => {
      this.updateForm(ciudad);

      this.paisService.query().subscribe((res: HttpResponse<IPais[]>) => (this.pais = res.body || []));
    });
  }

  updateForm(ciudad: ICiudad): void {
    this.editForm.patchValue({
      id: ciudad.id,
      nombreCiudad: ciudad.nombreCiudad,
      pais: ciudad.pais
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ciudad = this.createFromForm();
    if (ciudad.id !== undefined) {
      this.subscribeToSaveResponse(this.ciudadService.update(ciudad));
    } else {
      this.subscribeToSaveResponse(this.ciudadService.create(ciudad));
    }
  }

  private createFromForm(): ICiudad {
    return {
      ...new Ciudad(),
      id: this.editForm.get(['id'])!.value,
      nombreCiudad: this.editForm.get(['nombreCiudad'])!.value,
      pais: this.editForm.get(['pais'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICiudad>>): void {
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

  trackById(index: number, item: IPais): any {
    return item.id;
  }
}
