import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IModelo3D, Modelo3D } from 'app/shared/model/modelo-3-d.model';
import { Modelo3DService } from './modelo-3-d.service';
import { Modelo3DComponent } from './modelo-3-d.component';
import { Modelo3DDetailComponent } from './modelo-3-d-detail.component';
import { Modelo3DUpdateComponent } from './modelo-3-d-update.component';

@Injectable({ providedIn: 'root' })
export class Modelo3DResolve implements Resolve<IModelo3D> {
  constructor(private service: Modelo3DService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IModelo3D> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((modelo3D: HttpResponse<Modelo3D>) => {
          if (modelo3D.body) {
            return of(modelo3D.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Modelo3D());
  }
}

export const modelo3DRoute: Routes = [
  {
    path: '',
    component: Modelo3DComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.modelo3D.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: Modelo3DDetailComponent,
    resolve: {
      modelo3D: Modelo3DResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.modelo3D.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: Modelo3DUpdateComponent,
    resolve: {
      modelo3D: Modelo3DResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.modelo3D.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: Modelo3DUpdateComponent,
    resolve: {
      modelo3D: Modelo3DResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.modelo3D.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
