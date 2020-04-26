import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICategorias, Categorias } from 'app/shared/model/categorias.model';
import { CategoriasService } from './categorias.service';
import { CategoriasComponent } from './categorias.component';
import { CategoriasDetailComponent } from './categorias-detail.component';
import { CategoriasUpdateComponent } from './categorias-update.component';

@Injectable({ providedIn: 'root' })
export class CategoriasResolve implements Resolve<ICategorias> {
  constructor(private service: CategoriasService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategorias> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((categorias: HttpResponse<Categorias>) => {
          if (categorias.body) {
            return of(categorias.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Categorias());
  }
}

export const categoriasRoute: Routes = [
  {
    path: '',
    component: CategoriasComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.categorias.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CategoriasDetailComponent,
    resolve: {
      categorias: CategoriasResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.categorias.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CategoriasUpdateComponent,
    resolve: {
      categorias: CategoriasResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.categorias.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CategoriasUpdateComponent,
    resolve: {
      categorias: CategoriasResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.categorias.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
