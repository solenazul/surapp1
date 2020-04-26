import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Surapp1SharedModule } from 'app/shared/shared.module';
import { ComentariosServiciosComponent } from './comentarios-servicios.component';
import { ComentariosServiciosDetailComponent } from './comentarios-servicios-detail.component';
import { ComentariosServiciosUpdateComponent } from './comentarios-servicios-update.component';
import { ComentariosServiciosDeleteDialogComponent } from './comentarios-servicios-delete-dialog.component';
import { comentariosServiciosRoute } from './comentarios-servicios.route';

@NgModule({
  imports: [Surapp1SharedModule, RouterModule.forChild(comentariosServiciosRoute)],
  declarations: [
    ComentariosServiciosComponent,
    ComentariosServiciosDetailComponent,
    ComentariosServiciosUpdateComponent,
    ComentariosServiciosDeleteDialogComponent
  ],
  entryComponents: [ComentariosServiciosDeleteDialogComponent]
})
export class Surapp1ComentariosServiciosModule {}
