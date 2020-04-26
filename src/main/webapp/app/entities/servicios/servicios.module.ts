import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Surapp1SharedModule } from 'app/shared/shared.module';
import { ServiciosComponent } from './servicios.component';
import { ServiciosDetailComponent } from './servicios-detail.component';
import { ServiciosUpdateComponent } from './servicios-update.component';
import { ServiciosDeleteDialogComponent } from './servicios-delete-dialog.component';
import { serviciosRoute } from './servicios.route';

@NgModule({
  imports: [Surapp1SharedModule, RouterModule.forChild(serviciosRoute)],
  declarations: [ServiciosComponent, ServiciosDetailComponent, ServiciosUpdateComponent, ServiciosDeleteDialogComponent],
  entryComponents: [ServiciosDeleteDialogComponent]
})
export class Surapp1ServiciosModule {}
