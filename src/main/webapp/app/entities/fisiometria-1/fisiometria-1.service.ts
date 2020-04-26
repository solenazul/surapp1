import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFisiometria1 } from 'app/shared/model/fisiometria-1.model';

type EntityResponseType = HttpResponse<IFisiometria1>;
type EntityArrayResponseType = HttpResponse<IFisiometria1[]>;

@Injectable({ providedIn: 'root' })
export class Fisiometria1Service {
  public resourceUrl = SERVER_API_URL + 'api/fisiometria-1-s';

  constructor(protected http: HttpClient) {}

  create(fisiometria1: IFisiometria1): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fisiometria1);
    return this.http
      .post<IFisiometria1>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fisiometria1: IFisiometria1): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fisiometria1);
    return this.http
      .put<IFisiometria1>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFisiometria1>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFisiometria1[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(fisiometria1: IFisiometria1): IFisiometria1 {
    const copy: IFisiometria1 = Object.assign({}, fisiometria1, {
      timeInstant: fisiometria1.timeInstant && fisiometria1.timeInstant.isValid() ? fisiometria1.timeInstant.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.timeInstant = res.body.timeInstant ? moment(res.body.timeInstant) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((fisiometria1: IFisiometria1) => {
        fisiometria1.timeInstant = fisiometria1.timeInstant ? moment(fisiometria1.timeInstant) : undefined;
      });
    }
    return res;
  }
}
