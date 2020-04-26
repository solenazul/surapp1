import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Surapp1SharedModule } from 'app/shared/shared.module';
import { IPSComponent } from './ips.component';
import { IPSDetailComponent } from './ips-detail.component';
import { IPSUpdateComponent } from './ips-update.component';
import { IPSDeleteDialogComponent } from './ips-delete-dialog.component';
import { iPSRoute } from './ips.route';

@NgModule({
  imports: [Surapp1SharedModule, RouterModule.forChild(iPSRoute)],
  declarations: [IPSComponent, IPSDetailComponent, IPSUpdateComponent, IPSDeleteDialogComponent],
  entryComponents: [IPSDeleteDialogComponent]
})
export class Surapp1IPSModule {}
