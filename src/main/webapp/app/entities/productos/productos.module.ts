import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Surapp1SharedModule } from 'app/shared/shared.module';
import { ProductosComponent } from './productos.component';
import { ProductosDetailComponent } from './productos-detail.component';
import { ProductosUpdateComponent } from './productos-update.component';
import { ProductosDeleteDialogComponent } from './productos-delete-dialog.component';
import { productosRoute } from './productos.route';

@NgModule({
  imports: [Surapp1SharedModule, RouterModule.forChild(productosRoute)],
  declarations: [ProductosComponent, ProductosDetailComponent, ProductosUpdateComponent, ProductosDeleteDialogComponent],
  entryComponents: [ProductosDeleteDialogComponent]
})
export class Surapp1ProductosModule {}
