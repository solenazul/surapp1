import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFavoritosServicios, FavoritosServicios } from 'app/shared/model/favoritos-servicios.model';
import { FavoritosServiciosService } from './favoritos-servicios.service';
import { FavoritosServiciosComponent } from './favoritos-servicios.component';
import { FavoritosServiciosDetailComponent } from './favoritos-servicios-detail.component';
import { FavoritosServiciosUpdateComponent } from './favoritos-servicios-update.component';

@Injectable({ providedIn: 'root' })
export class FavoritosServiciosResolve implements Resolve<IFavoritosServicios> {
  constructor(private service: FavoritosServiciosService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFavoritosServicios> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((favoritosServicios: HttpResponse<FavoritosServicios>) => {
          if (favoritosServicios.body) {
            return of(favoritosServicios.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FavoritosServicios());
  }
}

export const favoritosServiciosRoute: Routes = [
  {
    path: '',
    component: FavoritosServiciosComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.favoritosServicios.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FavoritosServiciosDetailComponent,
    resolve: {
      favoritosServicios: FavoritosServiciosResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.favoritosServicios.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FavoritosServiciosUpdateComponent,
    resolve: {
      favoritosServicios: FavoritosServiciosResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.favoritosServicios.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FavoritosServiciosUpdateComponent,
    resolve: {
      favoritosServicios: FavoritosServiciosResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.favoritosServicios.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
