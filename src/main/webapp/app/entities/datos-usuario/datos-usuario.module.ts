import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Surapp1SharedModule } from 'app/shared/shared.module';
import { DatosUsuarioComponent } from './datos-usuario.component';
import { DatosUsuarioDetailComponent } from './datos-usuario-detail.component';
import { DatosUsuarioUpdateComponent } from './datos-usuario-update.component';
import { DatosUsuarioDeleteDialogComponent } from './datos-usuario-delete-dialog.component';
import { datosUsuarioRoute } from './datos-usuario.route';

@NgModule({
  imports: [Surapp1SharedModule, RouterModule.forChild(datosUsuarioRoute)],
  declarations: [DatosUsuarioComponent, DatosUsuarioDetailComponent, DatosUsuarioUpdateComponent, DatosUsuarioDeleteDialogComponent],
  entryComponents: [DatosUsuarioDeleteDialogComponent]
})
export class Surapp1DatosUsuarioModule {}
