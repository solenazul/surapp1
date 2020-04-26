import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICategoriaTablero, CategoriaTablero } from 'app/shared/model/categoria-tablero.model';
import { CategoriaTableroService } from './categoria-tablero.service';
import { CategoriaTableroComponent } from './categoria-tablero.component';
import { CategoriaTableroDetailComponent } from './categoria-tablero-detail.component';
import { CategoriaTableroUpdateComponent } from './categoria-tablero-update.component';

@Injectable({ providedIn: 'root' })
export class CategoriaTableroResolve implements Resolve<ICategoriaTablero> {
  constructor(private service: CategoriaTableroService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategoriaTablero> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((categoriaTablero: HttpResponse<CategoriaTablero>) => {
          if (categoriaTablero.body) {
            return of(categoriaTablero.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CategoriaTablero());
  }
}

export const categoriaTableroRoute: Routes = [
  {
    path: '',
    component: CategoriaTableroComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.categoriaTablero.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CategoriaTableroDetailComponent,
    resolve: {
      categoriaTablero: CategoriaTableroResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.categoriaTablero.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CategoriaTableroUpdateComponent,
    resolve: {
      categoriaTablero: CategoriaTableroResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.categoriaTablero.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CategoriaTableroUpdateComponent,
    resolve: {
      categoriaTablero: CategoriaTableroResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.categoriaTablero.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
