import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IServicios } from 'app/shared/model/servicios.model';

type EntityResponseType = HttpResponse<IServicios>;
type EntityArrayResponseType = HttpResponse<IServicios[]>;

@Injectable({ providedIn: 'root' })
export class ServiciosService {
  public resourceUrl = SERVER_API_URL + 'api/servicios';

  constructor(protected http: HttpClient) {}

  create(servicios: IServicios): Observable<EntityResponseType> {
    return this.http.post<IServicios>(this.resourceUrl, servicios, { observe: 'response' });
  }

  update(servicios: IServicios): Observable<EntityResponseType> {
    return this.http.put<IServicios>(this.resourceUrl, servicios, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IServicios>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServicios[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
