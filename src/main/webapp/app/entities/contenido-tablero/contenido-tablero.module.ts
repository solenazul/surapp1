import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Surapp1SharedModule } from 'app/shared/shared.module';
import { ContenidoTableroComponent } from './contenido-tablero.component';
import { ContenidoTableroDetailComponent } from './contenido-tablero-detail.component';
import { ContenidoTableroUpdateComponent } from './contenido-tablero-update.component';
import { ContenidoTableroDeleteDialogComponent } from './contenido-tablero-delete-dialog.component';
import { contenidoTableroRoute } from './contenido-tablero.route';

@NgModule({
  imports: [Surapp1SharedModule, RouterModule.forChild(contenidoTableroRoute)],
  declarations: [
    ContenidoTableroComponent,
    ContenidoTableroDetailComponent,
    ContenidoTableroUpdateComponent,
    ContenidoTableroDeleteDialogComponent
  ],
  entryComponents: [ContenidoTableroDeleteDialogComponent]
})
export class Surapp1ContenidoTableroModule {}
