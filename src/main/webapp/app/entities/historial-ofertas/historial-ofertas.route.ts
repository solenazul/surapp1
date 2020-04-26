import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IHistorialOfertas, HistorialOfertas } from 'app/shared/model/historial-ofertas.model';
import { HistorialOfertasService } from './historial-ofertas.service';
import { HistorialOfertasComponent } from './historial-ofertas.component';
import { HistorialOfertasDetailComponent } from './historial-ofertas-detail.component';
import { HistorialOfertasUpdateComponent } from './historial-ofertas-update.component';

@Injectable({ providedIn: 'root' })
export class HistorialOfertasResolve implements Resolve<IHistorialOfertas> {
  constructor(private service: HistorialOfertasService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHistorialOfertas> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((historialOfertas: HttpResponse<HistorialOfertas>) => {
          if (historialOfertas.body) {
            return of(historialOfertas.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HistorialOfertas());
  }
}

export const historialOfertasRoute: Routes = [
  {
    path: '',
    component: HistorialOfertasComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.historialOfertas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: HistorialOfertasDetailComponent,
    resolve: {
      historialOfertas: HistorialOfertasResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.historialOfertas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: HistorialOfertasUpdateComponent,
    resolve: {
      historialOfertas: HistorialOfertasResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.historialOfertas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: HistorialOfertasUpdateComponent,
    resolve: {
      historialOfertas: HistorialOfertasResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.historialOfertas.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
