import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEncuestaEntorno } from 'app/shared/model/encuesta-entorno.model';

type EntityResponseType = HttpResponse<IEncuestaEntorno>;
type EntityArrayResponseType = HttpResponse<IEncuestaEntorno[]>;

@Injectable({ providedIn: 'root' })
export class EncuestaEntornoService {
  public resourceUrl = SERVER_API_URL + 'api/encuesta-entornos';

  constructor(protected http: HttpClient) {}

  create(encuestaEntorno: IEncuestaEntorno): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(encuestaEntorno);
    return this.http
      .post<IEncuestaEntorno>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(encuestaEntorno: IEncuestaEntorno): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(encuestaEntorno);
    return this.http
      .put<IEncuestaEntorno>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEncuestaEntorno>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEncuestaEntorno[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(encuestaEntorno: IEncuestaEntorno): IEncuestaEntorno {
    const copy: IEncuestaEntorno = Object.assign({}, encuestaEntorno, {
      fecha: encuestaEntorno.fecha && encuestaEntorno.fecha.isValid() ? encuestaEntorno.fecha.toJSON() : undefined
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
      res.body.forEach((encuestaEntorno: IEncuestaEntorno) => {
        encuestaEntorno.fecha = encuestaEntorno.fecha ? moment(encuestaEntorno.fecha) : undefined;
      });
    }
    return res;
  }
}
