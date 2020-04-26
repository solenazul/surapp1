import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Surapp1SharedModule } from 'app/shared/shared.module';
import { CategoriaTableroComponent } from './categoria-tablero.component';
import { CategoriaTableroDetailComponent } from './categoria-tablero-detail.component';
import { CategoriaTableroUpdateComponent } from './categoria-tablero-update.component';
import { CategoriaTableroDeleteDialogComponent } from './categoria-tablero-delete-dialog.component';
import { categoriaTableroRoute } from './categoria-tablero.route';

@NgModule({
  imports: [Surapp1SharedModule, RouterModule.forChild(categoriaTableroRoute)],
  declarations: [
    CategoriaTableroComponent,
    CategoriaTableroDetailComponent,
    CategoriaTableroUpdateComponent,
    CategoriaTableroDeleteDialogComponent
  ],
  entryComponents: [CategoriaTableroDeleteDialogComponent]
})
export class Surapp1CategoriaTableroModule {}
