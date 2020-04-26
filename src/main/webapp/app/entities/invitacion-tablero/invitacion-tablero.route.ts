import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInvitacionTablero, InvitacionTablero } from 'app/shared/model/invitacion-tablero.model';
import { InvitacionTableroService } from './invitacion-tablero.service';
import { InvitacionTableroComponent } from './invitacion-tablero.component';
import { InvitacionTableroDetailComponent } from './invitacion-tablero-detail.component';
import { InvitacionTableroUpdateComponent } from './invitacion-tablero-update.component';

@Injectable({ providedIn: 'root' })
export class InvitacionTableroResolve implements Resolve<IInvitacionTablero> {
  constructor(private service: InvitacionTableroService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInvitacionTablero> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((invitacionTablero: HttpResponse<InvitacionTablero>) => {
          if (invitacionTablero.body) {
            return of(invitacionTablero.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InvitacionTablero());
  }
}

export const invitacionTableroRoute: Routes = [
  {
    path: '',
    component: InvitacionTableroComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.invitacionTablero.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InvitacionTableroDetailComponent,
    resolve: {
      invitacionTablero: InvitacionTableroResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.invitacionTablero.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InvitacionTableroUpdateComponent,
    resolve: {
      invitacionTablero: InvitacionTableroResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.invitacionTablero.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InvitacionTableroUpdateComponent,
    resolve: {
      invitacionTablero: InvitacionTableroResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'surapp1App.invitacionTablero.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
