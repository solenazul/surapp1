import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAlarma, Alarma } from 'app/shared/model/alarma.model';
import { AlarmaService } from './alarma.service';
import { AlarmaComponent } from './alarma.component';
import { AlarmaDetailComponent } from './alarma-detail.component';
import { AlarmaUpdateComponent } from './alarma-update.component';

@Injectable({ providedIn: 'root' })
export class AlarmaResolve implements Resolve<IAlarma> {
  constructor(private service: AlarmaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAlarma> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((alarma: HttpResponse<Alarma>) => {
          if (alarma.body) {
            return of(alarma.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Alarma());
  }
}

export const alarmaRoute: Routes = [
  {
    path: '',
    component: AlarmaComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.alarma.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AlarmaDetailComponent,
    resolve: {
      alarma: AlarmaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.alarma.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AlarmaUpdateComponent,
    resolve: {
      alarma: AlarmaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.alarma.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AlarmaUpdateComponent,
    resolve: {
      alarma: AlarmaResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.alarma.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
