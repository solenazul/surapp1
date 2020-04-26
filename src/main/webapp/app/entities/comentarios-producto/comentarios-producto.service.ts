import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IComentariosProducto } from 'app/shared/model/comentarios-producto.model';

type EntityResponseType = HttpResponse<IComentariosProducto>;
type EntityArrayResponseType = HttpResponse<IComentariosProducto[]>;

@Injectable({ providedIn: 'root' })
export class ComentariosProductoService {
  public resourceUrl = SERVER_API_URL + 'api/comentarios-productos';

  constructor(protected http: HttpClient) {}

  create(comentariosProducto: IComentariosProducto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(comentariosProducto);
    return this.http
      .post<IComentariosProducto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(comentariosProducto: IComentariosProducto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(comentariosProducto);
    return this.http
      .put<IComentariosProducto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IComentariosProducto>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IComentariosProducto[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(comentariosProducto: IComentariosProducto): IComentariosProducto {
    const copy: IComentariosProducto = Object.assign({}, comentariosProducto, {
      fecha: comentariosProducto.fecha && comentariosProducto.fecha.isValid() ? comentariosProducto.fecha.toJSON() : undefined
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
      res.body.forEach((comentariosProducto: IComentariosProducto) => {
        comentariosProducto.fecha = comentariosProducto.fecha ? moment(comentariosProducto.fecha) : undefined;
      });
    }
    return res;
  }
}
