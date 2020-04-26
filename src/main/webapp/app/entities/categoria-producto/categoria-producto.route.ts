import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICategoriaProducto, CategoriaProducto } from 'app/shared/model/categoria-producto.model';
import { CategoriaProductoService } from './categoria-producto.service';
import { CategoriaProductoComponent } from './categoria-producto.component';
import { CategoriaProductoDetailComponent } from './categoria-producto-detail.component';
import { CategoriaProductoUpdateComponent } from './categoria-producto-update.component';

@Injectable({ providedIn: 'root' })
export class CategoriaProductoResolve implements Resolve<ICategoriaProducto> {
  constructor(private service: CategoriaProductoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategoriaProducto> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((categoriaProducto: HttpResponse<CategoriaProducto>) => {
          if (categoriaProducto.body) {
            return of(categoriaProducto.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CategoriaProducto());
  }
}

export const categoriaProductoRoute: Routes = [
  {
    path: '',
    component: CategoriaProductoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.categoriaProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CategoriaProductoDetailComponent,
    resolve: {
      categoriaProducto: CategoriaProductoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.categoriaProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CategoriaProductoUpdateComponent,
    resolve: {
      categoriaProducto: CategoriaProductoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.categoriaProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CategoriaProductoUpdateComponent,
    resolve: {
      categoriaProducto: CategoriaProductoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.categoriaProducto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
