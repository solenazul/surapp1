import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFisiometria1, Fisiometria1 } from 'app/shared/model/fisiometria-1.model';
import { Fisiometria1Service } from './fisiometria-1.service';
import { Fisiometria1Component } from './fisiometria-1.component';
import { Fisiometria1DetailComponent } from './fisiometria-1-detail.component';
import { Fisiometria1UpdateComponent } from './fisiometria-1-update.component';

@Injectable({ providedIn: 'root' })
export class Fisiometria1Resolve implements Resolve<IFisiometria1> {
  constructor(private service: Fisiometria1Service, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFisiometria1> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((fisiometria1: HttpResponse<Fisiometria1>) => {
          if (fisiometria1.body) {
            return of(fisiometria1.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Fisiometria1());
  }
}

export const fisiometria1Route: Routes = [
  {
    path: '',
    component: Fisiometria1Component,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.fisiometria1.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: Fisiometria1DetailComponent,
    resolve: {
      fisiometria1: Fisiometria1Resolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.fisiometria1.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: Fisiometria1UpdateComponent,
    resolve: {
      fisiometria1: Fisiometria1Resolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.fisiometria1.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: Fisiometria1UpdateComponent,
    resolve: {
      fisiometria1: Fisiometria1Resolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.fisiometria1.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
