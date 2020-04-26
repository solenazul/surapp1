import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContenidoTablero } from 'app/shared/model/contenido-tablero.model';

type EntityResponseType = HttpResponse<IContenidoTablero>;
type EntityArrayResponseType = HttpResponse<IContenidoTablero[]>;

@Injectable({ providedIn: 'root' })
export class ContenidoTableroService {
  public resourceUrl = SERVER_API_URL + 'api/contenido-tableros';

  constructor(protected http: HttpClient) {}

  create(contenidoTablero: IContenidoTablero): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contenidoTablero);
    return this.http
      .post<IContenidoTablero>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(contenidoTablero: IContenidoTablero): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contenidoTablero);
    return this.http
      .put<IContenidoTablero>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IContenidoTablero>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IContenidoTablero[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(contenidoTablero: IContenidoTablero): IContenidoTablero {
    const copy: IContenidoTablero = Object.assign({}, contenidoTablero, {
      fecha: contenidoTablero.fecha && contenidoTablero.fecha.isValid() ? contenidoTablero.fecha.toJSON() : undefined
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
      res.body.forEach((contenidoTablero: IContenidoTablero) => {
        contenidoTablero.fecha = contenidoTablero.fecha ? moment(contenidoTablero.fecha) : undefined;
      });
    }
    return res;
  }
}
