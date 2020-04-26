import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Surapp1SharedModule } from 'app/shared/shared.module';
import { TablerosComponent } from './tableros.component';
import { TablerosDetailComponent } from './tableros-detail.component';
import { TablerosUpdateComponent } from './tableros-update.component';
import { TablerosDeleteDialogComponent } from './tableros-delete-dialog.component';
import { tablerosRoute } from './tableros.route';

@NgModule({
  imports: [Surapp1SharedModule, RouterModule.forChild(tablerosRoute)],
  declarations: [TablerosComponent, TablerosDetailComponent, TablerosUpdateComponent, TablerosDeleteDialogComponent],
  entryComponents: [TablerosDeleteDialogComponent]
})
export class Surapp1TablerosModule {}
