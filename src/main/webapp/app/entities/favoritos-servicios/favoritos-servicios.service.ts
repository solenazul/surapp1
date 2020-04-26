import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFavoritosServicios } from 'app/shared/model/favoritos-servicios.model';

type EntityResponseType = HttpResponse<IFavoritosServicios>;
type EntityArrayResponseType = HttpResponse<IFavoritosServicios[]>;

@Injectable({ providedIn: 'root' })
export class FavoritosServiciosService {
  public resourceUrl = SERVER_API_URL + 'api/favoritos-servicios';

  constructor(protected http: HttpClient) {}

  create(favoritosServicios: IFavoritosServicios): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(favoritosServicios);
    return this.http
      .post<IFavoritosServicios>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(favoritosServicios: IFavoritosServicios): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(favoritosServicios);
    return this.http
      .put<IFavoritosServicios>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFavoritosServicios>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFavoritosServicios[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(favoritosServicios: IFavoritosServicios): IFavoritosServicios {
    const copy: IFavoritosServicios = Object.assign({}, favoritosServicios, {
      fecha: favoritosServicios.fecha && favoritosServicios.fecha.isValid() ? favoritosServicios.fecha.format(DATE_FORMAT) : undefined
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
      res.body.forEach((favoritosServicios: IFavoritosServicios) => {
        favoritosServicios.fecha = favoritosServicios.fecha ? moment(favoritosServicios.fecha) : undefined;
      });
    }
    return res;
  }
}
