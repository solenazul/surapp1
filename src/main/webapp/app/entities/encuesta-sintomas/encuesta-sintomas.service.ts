import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEncuestaSintomas } from 'app/shared/model/encuesta-sintomas.model';

type EntityResponseType = HttpResponse<IEncuestaSintomas>;
type EntityArrayResponseType = HttpResponse<IEncuestaSintomas[]>;

@Injectable({ providedIn: 'root' })
export class EncuestaSintomasService {
  public resourceUrl = SERVER_API_URL + 'api/encuesta-sintomas';

  constructor(protected http: HttpClient) {}

  create(encuestaSintomas: IEncuestaSintomas): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(encuestaSintomas);
    return this.http
      .post<IEncuestaSintomas>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(encuestaSintomas: IEncuestaSintomas): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(encuestaSintomas);
    return this.http
      .put<IEncuestaSintomas>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEncuestaSintomas>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEncuestaSintomas[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(encuestaSintomas: IEncuestaSintomas): IEncuestaSintomas {
    const copy: IEncuestaSintomas = Object.assign({}, encuestaSintomas, {
      fecha: encuestaSintomas.fecha && encuestaSintomas.fecha.isValid() ? encuestaSintomas.fecha.toJSON() : undefined
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
      res.body.forEach((encuestaSintomas: IEncuestaSintomas) => {
        encuestaSintomas.fecha = encuestaSintomas.fecha ? moment(encuestaSintomas.fecha) : undefined;
      });
    }
    return res;
  }
}
