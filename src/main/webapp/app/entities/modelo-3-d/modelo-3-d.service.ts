import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IModelo3D } from 'app/shared/model/modelo-3-d.model';

type EntityResponseType = HttpResponse<IModelo3D>;
type EntityArrayResponseType = HttpResponse<IModelo3D[]>;

@Injectable({ providedIn: 'root' })
export class Modelo3DService {
  public resourceUrl = SERVER_API_URL + 'api/modelo-3-ds';

  constructor(protected http: HttpClient) {}

  create(modelo3D: IModelo3D): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(modelo3D);
    return this.http
      .post<IModelo3D>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(modelo3D: IModelo3D): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(modelo3D);
    return this.http
      .put<IModelo3D>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IModelo3D>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IModelo3D[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(modelo3D: IModelo3D): IModelo3D {
    const copy: IModelo3D = Object.assign({}, modelo3D, {
      fecha: modelo3D.fecha && modelo3D.fecha.isValid() ? modelo3D.fecha.format(DATE_FORMAT) : undefined
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
      res.body.forEach((modelo3D: IModelo3D) => {
        modelo3D.fecha = modelo3D.fecha ? moment(modelo3D.fecha) : undefined;
      });
    }
    return res;
  }
}
