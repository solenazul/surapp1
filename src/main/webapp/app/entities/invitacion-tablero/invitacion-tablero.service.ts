import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInvitacionTablero } from 'app/shared/model/invitacion-tablero.model';

type EntityResponseType = HttpResponse<IInvitacionTablero>;
type EntityArrayResponseType = HttpResponse<IInvitacionTablero[]>;

@Injectable({ providedIn: 'root' })
export class InvitacionTableroService {
  public resourceUrl = SERVER_API_URL + 'api/invitacion-tableros';

  constructor(protected http: HttpClient) {}

  create(invitacionTablero: IInvitacionTablero): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invitacionTablero);
    return this.http
      .post<IInvitacionTablero>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(invitacionTablero: IInvitacionTablero): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invitacionTablero);
    return this.http
      .put<IInvitacionTablero>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInvitacionTablero>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInvitacionTablero[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(invitacionTablero: IInvitacionTablero): IInvitacionTablero {
    const copy: IInvitacionTablero = Object.assign({}, invitacionTablero, {
      fecha: invitacionTablero.fecha && invitacionTablero.fecha.isValid() ? invitacionTablero.fecha.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha ? moment(res.body.fecha) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((invitacionTablero: IInvitacionTablero) => {
        invitacionTablero.fecha = invitacionTablero.fecha ? moment(invitacionTablero.fecha) : undefined;
      });
    }
    return res;
  }
}