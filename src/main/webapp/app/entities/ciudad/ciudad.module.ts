import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Surapp1SharedModule } from 'app/shared/shared.module';
import { CiudadComponent } from './ciudad.component';
import { CiudadDetailComponent } from './ciudad-detail.component';
import { CiudadUpdateComponent } from './ciudad-update.component';
import { CiudadDeleteDialogComponent } from './ciudad-delete-dialog.component';
import { ciudadRoute } from './ciudad.route';

@NgModule({
  imports: [Surapp1SharedModule, RouterModule.forChild(ciudadRoute)],
  declarations: [CiudadComponent, CiudadDetailComponent, CiudadUpdateComponent, CiudadDeleteDialogComponent],
  entryComponents: [CiudadDeleteDialogComponent]
})
export class Surapp1CiudadModule {}
