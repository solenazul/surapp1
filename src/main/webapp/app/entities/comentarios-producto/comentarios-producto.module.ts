import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Surapp1SharedModule } from 'app/shared/shared.module';
import { ComentariosProductoComponent } from './comentarios-producto.component';
import { ComentariosProductoDetailComponent } from './comentarios-producto-detail.component';
import { ComentariosProductoUpdateComponent } from './comentarios-producto-update.component';
import { ComentariosProductoDeleteDialogComponent } from './comentarios-producto-delete-dialog.component';
import { comentariosProductoRoute } from './comentarios-producto.route';

@NgModule({
  imports: [Surapp1SharedModule, RouterModule.forChild(comentariosProductoRoute)],
  declarations: [
    ComentariosProductoComponent,
    ComentariosProductoDetailComponent,
    ComentariosProductoUpdateComponent,
    ComentariosProductoDeleteDialogComponent
  ],
  entryComponents: [ComentariosProductoDeleteDialogComponent]
})
export class Surapp1ComentariosProductoModule {}
