import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Surapp1SharedModule } from 'app/shared/shared.module';
import { CategoriaProductoComponent } from './categoria-producto.component';
import { CategoriaProductoDetailComponent } from './categoria-producto-detail.component';
import { CategoriaProductoUpdateComponent } from './categoria-producto-update.component';
import { CategoriaProductoDeleteDialogComponent } from './categoria-producto-delete-dialog.component';
import { categoriaProductoRoute } from './categoria-producto.route';

@NgModule({
  imports: [Surapp1SharedModule, RouterModule.forChild(categoriaProductoRoute)],
  declarations: [
    CategoriaProductoComponent,
    CategoriaProductoDetailComponent,
    CategoriaProductoUpdateComponent,
    CategoriaProductoDeleteDialogComponent
  ],
  entryComponents: [CategoriaProductoDeleteDialogComponent]
})
export class Surapp1CategoriaProductoModule {}
