import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Surapp1SharedModule } from 'app/shared/shared.module';
import { EncuestaSintomasComponent } from './encuesta-sintomas.component';
import { EncuestaSintomasDetailComponent } from './encuesta-sintomas-detail.component';
import { EncuestaSintomasUpdateComponent } from './encuesta-sintomas-update.component';
import { EncuestaSintomasDeleteDialogComponent } from './encuesta-sintomas-delete-dialog.component';
import { encuestaSintomasRoute } from './encuesta-sintomas.route';

@NgModule({
  imports: [Surapp1SharedModule, RouterModule.forChild(encuestaSintomasRoute)],
  declarations: [
    EncuestaSintomasComponent,
    EncuestaSintomasDetailComponent,
    EncuestaSintomasUpdateComponent,
    EncuestaSintomasDeleteDialogComponent
  ],
  entryComponents: [EncuestaSintomasDeleteDialogComponent]
})
export class Surapp1EncuestaSintomasModule {}
