import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICategorias } from 'app/shared/model/categorias.model';

type EntityResponseType = HttpResponse<ICategorias>;
type EntityArrayResponseType = HttpResponse<ICategorias[]>;

@Injectable({ providedIn: 'root' })
export class CategoriasService {
  public resourceUrl = SERVER_API_URL + 'api/categorias';

  constructor(protected http: HttpClient) {}

  create(categorias: ICategorias): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(categorias);
    return this.http
      .post<ICategorias>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(categorias: ICategorias): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(categorias);
    return this.http
      .put<ICategorias>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICategorias>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICategorias[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(categorias: ICategorias): ICategorias {
    const copy: ICategorias = Object.assign({}, categorias, {
      fecha: categorias.fecha && categorias.fecha.isValid() ? categorias.fecha.toJSON() : undefined
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
      res.body.forEach((categorias: ICategorias) => {
        categorias.fecha = categorias.fecha ? moment(categorias.fecha) : undefined;
      });
    }
    return res;
  }
}
