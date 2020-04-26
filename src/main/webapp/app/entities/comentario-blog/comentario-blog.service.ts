import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IComentarioBlog } from 'app/shared/model/comentario-blog.model';

type EntityResponseType = HttpResponse<IComentarioBlog>;
type EntityArrayResponseType = HttpResponse<IComentarioBlog[]>;

@Injectable({ providedIn: 'root' })
export class ComentarioBlogService {
  public resourceUrl = SERVER_API_URL + 'api/comentario-blogs';

  constructor(protected http: HttpClient) {}

  create(comentarioBlog: IComentarioBlog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(comentarioBlog);
    return this.http
      .post<IComentarioBlog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(comentarioBlog: IComentarioBlog): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(comentarioBlog);
    return this.http
      .put<IComentarioBlog>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IComentarioBlog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IComentarioBlog[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(comentarioBlog: IComentarioBlog): IComentarioBlog {
    const copy: IComentarioBlog = Object.assign({}, comentarioBlog, {
      fecha: comentarioBlog.fecha && comentarioBlog.fecha.isValid() ? comentarioBlog.fecha.toJSON() : undefined
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
      res.body.forEach((comentarioBlog: IComentarioBlog) => {
        comentarioBlog.fecha = comentarioBlog.fecha ? moment(comentarioBlog.fecha) : undefined;
      });
    }
    return res;
  }
}
