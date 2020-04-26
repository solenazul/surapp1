import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFavoritosProductos } from 'app/shared/model/favoritos-productos.model';

type EntityResponseType = HttpResponse<IFavoritosProductos>;
type EntityArrayResponseType = HttpResponse<IFavoritosProductos[]>;

@Injectable({ providedIn: 'root' })
export class FavoritosProductosService {
  public resourceUrl = SERVER_API_URL + 'api/favoritos-productos';

  constructor(protected http: HttpClient) {}

  create(favoritosProductos: IFavoritosProductos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(favoritosProductos);
    return this.http
      .post<IFavoritosProductos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(favoritosProductos: IFavoritosProductos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(favoritosProductos);
    return this.http
      .put<IFavoritosProductos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFavoritosProductos>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFavoritosProductos[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(favoritosProductos: IFavoritosProductos): IFavoritosProductos {
    const copy: IFavoritosProductos = Object.assign({}, favoritosProductos, {
      fecha: favoritosProductos.fecha && favoritosProductos.fecha.isValid() ? favoritosProductos.fecha.format(DATE_FORMAT) : undefined
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
      res.body.forEach((favoritosProductos: IFavoritosProductos) => {
        favoritosProductos.fecha = favoritosProductos.fecha ? moment(favoritosProductos.fecha) : undefined;
      });
    }
    return res;
  }
}
