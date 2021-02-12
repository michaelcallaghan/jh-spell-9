import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPgc } from 'app/shared/model/pgc.model';

type EntityResponseType = HttpResponse<IPgc>;
type EntityArrayResponseType = HttpResponse<IPgc[]>;

@Injectable({ providedIn: 'root' })
export class PgcService {
  public resourceUrl = SERVER_API_URL + 'api/pgcs';

  constructor(protected http: HttpClient) {}

  create(pgc: IPgc): Observable<EntityResponseType> {
    return this.http.post<IPgc>(this.resourceUrl, pgc, { observe: 'response' });
  }

  update(pgc: IPgc): Observable<EntityResponseType> {
    return this.http.put<IPgc>(this.resourceUrl, pgc, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPgc>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPgc[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
