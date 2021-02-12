import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPgclo } from 'app/shared/model/pgclo.model';

type EntityResponseType = HttpResponse<IPgclo>;
type EntityArrayResponseType = HttpResponse<IPgclo[]>;

@Injectable({ providedIn: 'root' })
export class PgcloService {
  public resourceUrl = SERVER_API_URL + 'api/pgclos';

  constructor(protected http: HttpClient) {}

  create(pgclo: IPgclo): Observable<EntityResponseType> {
    return this.http.post<IPgclo>(this.resourceUrl, pgclo, { observe: 'response' });
  }

  update(pgclo: IPgclo): Observable<EntityResponseType> {
    return this.http.put<IPgclo>(this.resourceUrl, pgclo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPgclo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPgclo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
