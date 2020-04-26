import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Surapp1SharedModule } from 'app/shared/shared.module';
import { Modelo3DComponent } from './modelo-3-d.component';
import { Modelo3DDetailComponent } from './modelo-3-d-detail.component';
import { Modelo3DUpdateComponent } from './modelo-3-d-update.component';
import { Modelo3DDeleteDialogComponent } from './modelo-3-d-delete-dialog.component';
import { modelo3DRoute } from './modelo-3-d.route';

@NgModule({
  imports: [Surapp1SharedModule, RouterModule.forChild(modelo3DRoute)],
  declarations: [Modelo3DComponent, Modelo3DDetailComponent, Modelo3DUpdateComponent, Modelo3DDeleteDialogComponent],
  entryComponents: [Modelo3DDeleteDialogComponent]
})
export class Surapp1Modelo3DModule {}
