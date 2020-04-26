import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IComentarioBlog, ComentarioBlog } from 'app/shared/model/comentario-blog.model';
import { ComentarioBlogService } from './comentario-blog.service';
import { ComentarioBlogComponent } from './comentario-blog.component';
import { ComentarioBlogDetailComponent } from './comentario-blog-detail.component';
import { ComentarioBlogUpdateComponent } from './comentario-blog-update.component';

@Injectable({ providedIn: 'root' })
export class ComentarioBlogResolve implements Resolve<IComentarioBlog> {
  constructor(private service: ComentarioBlogService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IComentarioBlog> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((comentarioBlog: HttpResponse<ComentarioBlog>) => {
          if (comentarioBlog.body) {
            return of(comentarioBlog.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ComentarioBlog());
  }
}

export const comentarioBlogRoute: Routes = [
  {
    path: '',
    component: ComentarioBlogComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.comentarioBlog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ComentarioBlogDetailComponent,
    resolve: {
      comentarioBlog: ComentarioBlogResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.comentarioBlog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ComentarioBlogUpdateComponent,
    resolve: {
      comentarioBlog: ComentarioBlogResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.comentarioBlog.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ComentarioBlogUpdateComponent,
    resolve: {
      comentarioBlog: ComentarioBlogResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.comentarioBlog.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
