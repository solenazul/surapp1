import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IComentariosServicios } from 'app/shared/model/comentarios-servicios.model';

type EntityResponseType = HttpResponse<IComentariosServicios>;
type EntityArrayResponseType = HttpResponse<IComentariosServicios[]>;

@Injectable({ providedIn: 'root' })
export class ComentariosServiciosService {
  public resourceUrl = SERVER_API_URL + 'api/comentarios-servicios';

  constructor(protected http: HttpClient) {}

  create(comentariosServicios: IComentariosServicios): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(comentariosServicios);
    return this.http
      .post<IComentariosServicios>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(comentariosServicios: IComentariosServicios): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(comentariosServicios);
    return this.http
      .put<IComentariosServicios>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IComentariosServicios>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IComentariosServicios[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(comentariosServicios: IComentariosServicios): IComentariosServicios {
    const copy: IComentariosServicios = Object.assign({}, comentariosServicios, {
      fecha: comentariosServicios.fecha && comentariosServicios.fecha.isValid() ? comentariosServicios.fecha.toJSON() : undefined
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
      res.body.forEach((comentariosServicios: IComentariosServicios) => {
        comentariosServicios.fecha = comentariosServicios.fecha ? moment(comentariosServicios.fecha) : undefined;
      });
    }
    return res;
  }
}
