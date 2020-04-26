import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'fisiometria-1',
        loadChildren: () => import('./fisiometria-1/fisiometria-1.module').then(m => m.Surapp1Fisiometria1Module)
      },
      {
        path: 'alarma',
        loadChildren: () => import('./alarma/alarma.module').then(m => m.Surapp1AlarmaModule)
      },
      {
        path: 'paciente',
        loadChildren: () => import('./paciente/paciente.module').then(m => m.Surapp1PacienteModule)
      },
      {
        path: 'ips',
        loadChildren: () => import('./ips/ips.module').then(m => m.Surapp1IPSModule)
      },
      {
        path: 'encuesta-sintomas',
        loadChildren: () => import('./encuesta-sintomas/encuesta-sintomas.module').then(m => m.Surapp1EncuestaSintomasModule)
      },
      {
        path: 'encuesta-entorno',
        loadChildren: () => import('./encuesta-entorno/encuesta-entorno.module').then(m => m.Surapp1EncuestaEntornoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class Surapp1EntityModule {}
