import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IHomophonePair } from 'app/shared/model/homophone-pair.model';

type EntityResponseType = HttpResponse<IHomophonePair>;
type EntityArrayResponseType = HttpResponse<IHomophonePair[]>;

@Injectable({ providedIn: 'root' })
export class HomophonePairService {
  public resourceUrl = SERVER_API_URL + 'api/homophone-pairs';

  constructor(protected http: HttpClient) {}

  create(homophonePair: IHomophonePair): Observable<EntityResponseType> {
    return this.http.post<IHomophonePair>(this.resourceUrl, homophonePair, { observe: 'response' });
  }

  update(homophonePair: IHomophonePair): Observable<EntityResponseType> {
    return this.http.put<IHomophonePair>(this.resourceUrl, homophonePair, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHomophonePair>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHomophonePair[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
