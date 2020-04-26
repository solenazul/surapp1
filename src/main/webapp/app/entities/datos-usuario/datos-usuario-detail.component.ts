import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDatosUsuario } from 'app/shared/model/datos-usuario.model';

@Component({
  selector: 'jhi-datos-usuario-detail',
  templateUrl: './datos-usuario-detail.component.html'
})
export class DatosUsuarioDetailComponent implements OnInit {
  datosUsuario: IDatosUsuario | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ datosUsuario }) => (this.datosUsuario = datosUsuario));
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
