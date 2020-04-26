import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Surapp1SharedModule } from 'app/shared/shared.module';
import { FavoritosProductosComponent } from './favoritos-productos.component';
import { FavoritosProductosDetailComponent } from './favoritos-productos-detail.component';
import { FavoritosProductosUpdateComponent } from './favoritos-productos-update.component';
import { FavoritosProductosDeleteDialogComponent } from './favoritos-productos-delete-dialog.component';
import { favoritosProductosRoute } from './favoritos-productos.route';

@NgModule({
  imports: [Surapp1SharedModule, RouterModule.forChild(favoritosProductosRoute)],
  declarations: [
    FavoritosProductosComponent,
    FavoritosProductosDetailComponent,
    FavoritosProductosUpdateComponent,
    FavoritosProductosDeleteDialogComponent
  ],
  entryComponents: [FavoritosProductosDeleteDialogComponent]
})
export class Surapp1FavoritosProductosModule {}
