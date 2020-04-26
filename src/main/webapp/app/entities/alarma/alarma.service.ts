import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAlarma } from 'app/shared/model/alarma.model';

type EntityResponseType = HttpResponse<IAlarma>;
type EntityArrayResponseType = HttpResponse<IAlarma[]>;

@Injectable({ providedIn: 'root' })
export class AlarmaService {
  public resourceUrl = SERVER_API_URL + 'api/alarmas';

  constructor(protected http: HttpClient) {}

  create(alarma: IAlarma): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alarma);
    return this.http
      .post<IAlarma>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(alarma: IAlarma): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(alarma);
    return this.http
      .put<IAlarma>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAlarma>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAlarma[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(alarma: IAlarma): IAlarma {
    const copy: IAlarma = Object.assign({}, alarma, {
      timeInstant: alarma.timeInstant && alarma.timeInstant.isValid() ? alarma.timeInstant.toJSON() : undefined
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
      res.body.forEach((alarma: IAlarma) => {
        alarma.timeInstant = alarma.timeInstant ? moment(alarma.timeInstant) : undefined;
      });
    }
    return res;
  }
}
