import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPaciente } from 'app/shared/model/paciente.model';

@Component({
  selector: 'jhi-paciente-detail',
  templateUrl: './paciente-detail.component.html'
})
export class PacienteDetailComponent implements OnInit {
  paciente: IPaciente | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paciente }) => (this.paciente = paciente));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
