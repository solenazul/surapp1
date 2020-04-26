import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICategoriaProducto } from 'app/shared/model/categoria-producto.model';

type EntityResponseType = HttpResponse<ICategoriaProducto>;
type EntityArrayResponseType = HttpResponse<ICategoriaProducto[]>;

@Injectable({ providedIn: 'root' })
export class CategoriaProductoService {
  public resourceUrl = SERVER_API_URL + 'api/categoria-productos';

  constructor(protected http: HttpClient) {}

  create(categoriaProducto: ICategoriaProducto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(categoriaProducto);
    return this.http
      .post<ICategoriaProducto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(categoriaProducto: ICategoriaProducto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(categoriaProducto);
    return this.http
      .put<ICategoriaProducto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICategoriaProducto>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICategoriaProducto[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(categoriaProducto: ICategoriaProducto): ICategoriaProducto {
    const copy: ICategoriaProducto = Object.assign({}, categoriaProducto, {
      fecha: categoriaProducto.fecha && categoriaProducto.fecha.isValid() ? categoriaProducto.fecha.toJSON() : undefined
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
      res.body.forEach((categoriaProducto: ICategoriaProducto) => {
        categoriaProducto.fecha = categoriaProducto.fecha ? moment(categoriaProducto.fecha) : undefined;
      });
    }
    return res;
  }
}
