import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITableros, Tableros } from 'app/shared/model/tableros.model';
import { TablerosService } from './tableros.service';
import { TablerosComponent } from './tableros.component';
import { TablerosDetailComponent } from './tableros-detail.component';
import { TablerosUpdateComponent } from './tableros-update.component';

@Injectable({ providedIn: 'root' })
export class TablerosResolve implements Resolve<ITableros> {
  constructor(private service: TablerosService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITableros> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tableros: HttpResponse<Tableros>) => {
          if (tableros.body) {
            return of(tableros.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Tableros());
  }
}

export const tablerosRoute: Routes = [
  {
    path: '',
    component: TablerosComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.tableros.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TablerosDetailComponent,
    resolve: {
      tableros: TablerosResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.tableros.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TablerosUpdateComponent,
    resolve: {
      tableros: TablerosResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.tableros.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TablerosUpdateComponent,
    resolve: {
      tableros: TablerosResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.tableros.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
