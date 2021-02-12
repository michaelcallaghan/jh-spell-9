import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGrapheme } from 'app/shared/model/grapheme.model';

type EntityResponseType = HttpResponse<IGrapheme>;
type EntityArrayResponseType = HttpResponse<IGrapheme[]>;

@Injectable({ providedIn: 'root' })
export class GraphemeService {
  public resourceUrl = SERVER_API_URL + 'api/graphemes';

  constructor(protected http: HttpClient) {}

  create(grapheme: IGrapheme): Observable<EntityResponseType> {
    return this.http.post<IGrapheme>(this.resourceUrl, grapheme, { observe: 'response' });
  }

  update(grapheme: IGrapheme): Observable<EntityResponseType> {
    return this.http.put<IGrapheme>(this.resourceUrl, grapheme, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGrapheme>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGrapheme[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
