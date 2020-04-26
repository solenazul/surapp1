import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IServicios, Servicios } from 'app/shared/model/servicios.model';
import { ServiciosService } from './servicios.service';
import { ServiciosComponent } from './servicios.component';
import { ServiciosDetailComponent } from './servicios-detail.component';
import { ServiciosUpdateComponent } from './servicios-update.component';

@Injectable({ providedIn: 'root' })
export class ServiciosResolve implements Resolve<IServicios> {
  constructor(private service: ServiciosService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IServicios> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((servicios: HttpResponse<Servicios>) => {
          if (servicios.body) {
            return of(servicios.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Servicios());
  }
}

export const serviciosRoute: Routes = [
  {
    path: '',
    component: ServiciosComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.servicios.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiciosDetailComponent,
    resolve: {
      servicios: ServiciosResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.servicios.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiciosUpdateComponent,
    resolve: {
      servicios: ServiciosResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.servicios.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiciosUpdateComponent,
    resolve: {
      servicios: ServiciosResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.servicios.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
