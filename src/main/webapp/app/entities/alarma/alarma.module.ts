import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Surapp1SharedModule } from 'app/shared/shared.module';
import { AlarmaComponent } from './alarma.component';
import { AlarmaDetailComponent } from './alarma-detail.component';
import { AlarmaUpdateComponent } from './alarma-update.component';
import { AlarmaDeleteDialogComponent } from './alarma-delete-dialog.component';
import { alarmaRoute } from './alarma.route';

@NgModule({
  imports: [Surapp1SharedModule, RouterModule.forChild(alarmaRoute)],
  declarations: [AlarmaComponent, AlarmaDetailComponent, AlarmaUpdateComponent, AlarmaDeleteDialogComponent],
  entryComponents: [AlarmaDeleteDialogComponent]
})
export class Surapp1AlarmaModule {}
