import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Surapp1SharedModule } from 'app/shared/shared.module';
import { Fisiometria1Component } from './fisiometria-1.component';
import { Fisiometria1DetailComponent } from './fisiometria-1-detail.component';
import { Fisiometria1UpdateComponent } from './fisiometria-1-update.component';
import { Fisiometria1DeleteDialogComponent } from './fisiometria-1-delete-dialog.component';
import { fisiometria1Route } from './fisiometria-1.route';

@NgModule({
  imports: [Surapp1SharedModule, RouterModule.forChild(fisiometria1Route)],
  declarations: [Fisiometria1Component, Fisiometria1DetailComponent, Fisiometria1UpdateComponent, Fisiometria1DeleteDialogComponent],
  entryComponents: [Fisiometria1DeleteDialogComponent]
})
export class Surapp1Fisiometria1Module {}
