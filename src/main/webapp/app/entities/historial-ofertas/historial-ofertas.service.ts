import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IHistorialOfertas } from 'app/shared/model/historial-ofertas.model';

type EntityResponseType = HttpResponse<IHistorialOfertas>;
type EntityArrayResponseType = HttpResponse<IHistorialOfertas[]>;

@Injectable({ providedIn: 'root' })
export class HistorialOfertasService {
  public resourceUrl = SERVER_API_URL + 'api/historial-ofertas';

  constructor(protected http: HttpClient) {}

  create(historialOfertas: IHistorialOfertas): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(historialOfertas);
    return this.http
      .post<IHistorialOfertas>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(historialOfertas: IHistorialOfertas): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(historialOfertas);
    return this.http
      .put<IHistorialOfertas>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHistorialOfertas>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHistorialOfertas[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(historialOfertas: IHistorialOfertas): IHistorialOfertas {
    const copy: IHistorialOfertas = Object.assign({}, historialOfertas, {
      fechaInicial:
        historialOfertas.fechaInicial && historialOfertas.fechaInicial.isValid()
          ? historialOfertas.fechaInicial.format(DATE_FORMAT)
          : undefined,
      fechaFinal:
        historialOfertas.fechaFinal && historialOfertas.fechaFinal.isValid() ? historialOfertas.fechaFinal.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaInicial = res.body.fechaInicial ? moment(res.body.fechaInicial) : undefined;
      res.body.fechaFinal = res.body.fechaFinal ? moment(res.body.fechaFinal) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((historialOfertas: IHistorialOfertas) => {
        historialOfertas.fechaInicial = historialOfertas.fechaInicial ? moment(historialOfertas.fechaInicial) : undefined;
        historialOfertas.fechaFinal = historialOfertas.fechaFinal ? moment(historialOfertas.fechaFinal) : undefined;
      });
    }
    return res;
  }
}
