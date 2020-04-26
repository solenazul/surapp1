import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEncuestaSintomas, EncuestaSintomas } from 'app/shared/model/encuesta-sintomas.model';
import { EncuestaSintomasService } from './encuesta-sintomas.service';
import { EncuestaSintomasComponent } from './encuesta-sintomas.component';
import { EncuestaSintomasDetailComponent } from './encuesta-sintomas-detail.component';
import { EncuestaSintomasUpdateComponent } from './encuesta-sintomas-update.component';

@Injectable({ providedIn: 'root' })
export class EncuestaSintomasResolve implements Resolve<IEncuestaSintomas> {
  constructor(private service: EncuestaSintomasService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEncuestaSintomas> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((encuestaSintomas: HttpResponse<EncuestaSintomas>) => {
          if (encuestaSintomas.body) {
            return of(encuestaSintomas.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EncuestaSintomas());
  }
}

export const encuestaSintomasRoute: Routes = [
  {
    path: '',
    component: EncuestaSintomasComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.encuestaSintomas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EncuestaSintomasDetailComponent,
    resolve: {
      encuestaSintomas: EncuestaSintomasResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.encuestaSintomas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EncuestaSintomasUpdateComponent,
    resolve: {
      encuestaSintomas: EncuestaSintomasResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.encuestaSintomas.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EncuestaSintomasUpdateComponent,
    resolve: {
      encuestaSintomas: EncuestaSintomasResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.encuestaSintomas.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
