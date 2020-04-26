import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Surapp1SharedModule } from 'app/shared/shared.module';
import { FavoritosServiciosComponent } from './favoritos-servicios.component';
import { FavoritosServiciosDetailComponent } from './favoritos-servicios-detail.component';
import { FavoritosServiciosUpdateComponent } from './favoritos-servicios-update.component';
import { FavoritosServiciosDeleteDialogComponent } from './favoritos-servicios-delete-dialog.component';
import { favoritosServiciosRoute } from './favoritos-servicios.route';

@NgModule({
  imports: [Surapp1SharedModule, RouterModule.forChild(favoritosServiciosRoute)],
  declarations: [
    FavoritosServiciosComponent,
    FavoritosServiciosDetailComponent,
    FavoritosServiciosUpdateComponent,
    FavoritosServiciosDeleteDialogComponent
  ],
  entryComponents: [FavoritosServiciosDeleteDialogComponent]
})
export class Surapp1FavoritosServiciosModule {}
