import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Surapp1SharedModule } from 'app/shared/shared.module';
import { EncuestaEntornoComponent } from './encuesta-entorno.component';
import { EncuestaEntornoDetailComponent } from './encuesta-entorno-detail.component';
import { EncuestaEntornoUpdateComponent } from './encuesta-entorno-update.component';
import { EncuestaEntornoDeleteDialogComponent } from './encuesta-entorno-delete-dialog.component';
import { encuestaEntornoRoute } from './encuesta-entorno.route';

@NgModule({
  imports: [Surapp1SharedModule, RouterModule.forChild(encuestaEntornoRoute)],
  declarations: [
    EncuestaEntornoComponent,
    EncuestaEntornoDetailComponent,
    EncuestaEntornoUpdateComponent,
    EncuestaEntornoDeleteDialogComponent
  ],
  entryComponents: [EncuestaEntornoDeleteDialogComponent]
})
export class Surapp1EncuestaEntornoModule {}
