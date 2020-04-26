import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IComentariosServicios, ComentariosServicios } from 'app/shared/model/comentarios-servicios.model';
import { ComentariosServiciosService } from './comentarios-servicios.service';
import { ComentariosServiciosComponent } from './comentarios-servicios.component';
import { ComentariosServiciosDetailComponent } from './comentarios-servicios-detail.component';
import { ComentariosServiciosUpdateComponent } from './comentarios-servicios-update.component';

@Injectable({ providedIn: 'root' })
export class ComentariosServiciosResolve implements Resolve<IComentariosServicios> {
  constructor(private service: ComentariosServiciosService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IComentariosServicios> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((comentariosServicios: HttpResponse<ComentariosServicios>) => {
          if (comentariosServicios.body) {
            return of(comentariosServicios.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ComentariosServicios());
  }
}

export const comentariosServiciosRoute: Routes = [
  {
    path: '',
    component: ComentariosServiciosComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.comentariosServicios.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ComentariosServiciosDetailComponent,
    resolve: {
      comentariosServicios: ComentariosServiciosResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.comentariosServicios.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ComentariosServiciosUpdateComponent,
    resolve: {
      comentariosServicios: ComentariosServiciosResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.comentariosServicios.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ComentariosServiciosUpdateComponent,
    resolve: {
      comentariosServicios: ComentariosServiciosResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.comentariosServicios.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
