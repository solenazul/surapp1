import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContenidoTablero, ContenidoTablero } from 'app/shared/model/contenido-tablero.model';
import { ContenidoTableroService } from './contenido-tablero.service';
import { ContenidoTableroComponent } from './contenido-tablero.component';
import { ContenidoTableroDetailComponent } from './contenido-tablero-detail.component';
import { ContenidoTableroUpdateComponent } from './contenido-tablero-update.component';

@Injectable({ providedIn: 'root' })
export class ContenidoTableroResolve implements Resolve<IContenidoTablero> {
  constructor(private service: ContenidoTableroService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContenidoTablero> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contenidoTablero: HttpResponse<ContenidoTablero>) => {
          if (contenidoTablero.body) {
            return of(contenidoTablero.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ContenidoTablero());
  }
}

export const contenidoTableroRoute: Routes = [
  {
    path: '',
    component: ContenidoTableroComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.contenidoTablero.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContenidoTableroDetailComponent,
    resolve: {
      contenidoTablero: ContenidoTableroResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.contenidoTablero.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContenidoTableroUpdateComponent,
    resolve: {
      contenidoTablero: ContenidoTableroResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.contenidoTablero.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContenidoTableroUpdateComponent,
    resolve: {
      contenidoTablero: ContenidoTableroResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.contenidoTablero.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
