import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IComentariosProducto, ComentariosProducto } from 'app/shared/model/comentarios-producto.model';
import { ComentariosProductoService } from './comentarios-producto.service';
import { ComentariosProductoComponent } from './comentarios-producto.component';
import { ComentariosProductoDetailComponent } from './comentarios-producto-detail.component';
import { ComentariosProductoUpdateComponent } from './comentarios-producto-update.component';

@Injectable({ providedIn: 'root' })
export class ComentariosProductoResolve implements Resolve<IComentariosProducto> {
  constructor(private service: ComentariosProductoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IComentariosProducto> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((comentariosProducto: HttpResponse<ComentariosProducto>) => {
          if (comentariosProducto.body) {
            return of(comentariosProducto.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ComentariosProducto());
  }
}

export const comentariosProductoRoute: Routes = [
  {
    path: '',
    component: ComentariosProductoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.comentariosProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ComentariosProductoDetailComponent,
    resolve: {
      comentariosProducto: ComentariosProductoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.comentariosProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ComentariosProductoUpdateComponent,
    resolve: {
      comentariosProducto: ComentariosProductoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.comentariosProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ComentariosProductoUpdateComponent,
    resolve: {
      comentariosProducto: ComentariosProductoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.comentariosProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
