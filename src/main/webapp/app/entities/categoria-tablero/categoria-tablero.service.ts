import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICategoriaTablero } from 'app/shared/model/categoria-tablero.model';

type EntityResponseType = HttpResponse<ICategoriaTablero>;
type EntityArrayResponseType = HttpResponse<ICategoriaTablero[]>;

@Injectable({ providedIn: 'root' })
export class CategoriaTableroService {
  public resourceUrl = SERVER_API_URL + 'api/categoria-tableros';

  constructor(protected http: HttpClient) {}

  create(categoriaTablero: ICategoriaTablero): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(categoriaTablero);
    return this.http
      .post<ICategoriaTablero>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(categoriaTablero: ICategoriaTablero): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(categoriaTablero);
    return this.http
      .put<ICategoriaTablero>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICategoriaTablero>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICategoriaTablero[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(categoriaTablero: ICategoriaTablero): ICategoriaTablero {
    const copy: ICategoriaTablero = Object.assign({}, categoriaTablero, {
      fecha: categoriaTablero.fecha && categoriaTablero.fecha.isValid() ? categoriaTablero.fecha.toJSON() : undefined
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
      res.body.forEach((categoriaTablero: ICategoriaTablero) => {
        categoriaTablero.fecha = categoriaTablero.fecha ? moment(categoriaTablero.fecha) : undefined;
      });
    }
    return res;
  }
}
