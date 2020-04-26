import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Surapp1SharedModule } from 'app/shared/shared.module';
import { InvitacionTableroComponent } from './invitacion-tablero.component';
import { InvitacionTableroDetailComponent } from './invitacion-tablero-detail.component';
import { InvitacionTableroUpdateComponent } from './invitacion-tablero-update.component';
import { InvitacionTableroDeleteDialogComponent } from './invitacion-tablero-delete-dialog.component';
import { invitacionTableroRoute } from './invitacion-tablero.route';

@NgModule({
  imports: [Surapp1SharedModule, RouterModule.forChild(invitacionTableroRoute)],
  declarations: [
    InvitacionTableroComponent,
    InvitacionTableroDetailComponent,
    InvitacionTableroUpdateComponent,
    InvitacionTableroDeleteDialogComponent
  ],
  entryComponents: [InvitacionTableroDeleteDialogComponent]
})
export class Surapp1InvitacionTableroModule {}
