import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Surapp1SharedModule } from 'app/shared/shared.module';
import { ComentarioBlogComponent } from './comentario-blog.component';
import { ComentarioBlogDetailComponent } from './comentario-blog-detail.component';
import { ComentarioBlogUpdateComponent } from './comentario-blog-update.component';
import { ComentarioBlogDeleteDialogComponent } from './comentario-blog-delete-dialog.component';
import { comentarioBlogRoute } from './comentario-blog.route';

@NgModule({
  imports: [Surapp1SharedModule, RouterModule.forChild(comentarioBlogRoute)],
  declarations: [
    ComentarioBlogComponent,
    ComentarioBlogDetailComponent,
    ComentarioBlogUpdateComponent,
    ComentarioBlogDeleteDialogComponent
  ],
  entryComponents: [ComentarioBlogDeleteDialogComponent]
})
export class Surapp1ComentarioBlogModule {}
