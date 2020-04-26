import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Surapp1SharedModule } from 'app/shared/shared.module';
import { HistorialOfertasComponent } from './historial-ofertas.component';
import { HistorialOfertasDetailComponent } from './historial-ofertas-detail.component';
import { HistorialOfertasUpdateComponent } from './historial-ofertas-update.component';
import { HistorialOfertasDeleteDialogComponent } from './historial-ofertas-delete-dialog.component';
import { historialOfertasRoute } from './historial-ofertas.route';

@NgModule({
  imports: [Surapp1SharedModule, RouterModule.forChild(historialOfertasRoute)],
  declarations: [
    HistorialOfertasComponent,
    HistorialOfertasDetailComponent,
    HistorialOfertasUpdateComponent,
    HistorialOfertasDeleteDialogComponent
  ],
  entryComponents: [HistorialOfertasDeleteDialogComponent]
})
export class Surapp1HistorialOfertasModule {}
