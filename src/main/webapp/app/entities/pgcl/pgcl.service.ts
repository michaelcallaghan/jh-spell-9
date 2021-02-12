import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPgcl } from 'app/shared/model/pgcl.model';

type EntityResponseType = HttpResponse<IPgcl>;
type EntityArrayResponseType = HttpResponse<IPgcl[]>;

@Injectable({ providedIn: 'root' })
export class PgclService {
  public resourceUrl = SERVER_API_URL + 'api/pgcls';

  constructor(protected http: HttpClient) {}

  create(pgcl: IPgcl): Observable<EntityResponseType> {
    return this.http.post<IPgcl>(this.resourceUrl, pgcl, { observe: 'response' });
  }

  update(pgcl: IPgcl): Observable<EntityResponseType> {
    return this.http.put<IPgcl>(this.resourceUrl, pgcl, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPgcl>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPgcl[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
