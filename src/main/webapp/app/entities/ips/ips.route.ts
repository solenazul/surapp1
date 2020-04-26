import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIPS, IPS } from 'app/shared/model/ips.model';
import { IPSService } from './ips.service';
import { IPSComponent } from './ips.component';
import { IPSDetailComponent } from './ips-detail.component';
import { IPSUpdateComponent } from './ips-update.component';

@Injectable({ providedIn: 'root' })
export class IPSResolve implements Resolve<IIPS> {
  constructor(private service: IPSService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIPS> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((iPS: HttpResponse<IPS>) => {
          if (iPS.body) {
            return of(iPS.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IPS());
  }
}

export const iPSRoute: Routes = [
  {
    path: '',
    component: IPSComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.iPS.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IPSDetailComponent,
    resolve: {
      iPS: IPSResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.iPS.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IPSUpdateComponent,
    resolve: {
      iPS: IPSResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.iPS.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IPSUpdateComponent,
    resolve: {
      iPS: IPSResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.iPS.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
