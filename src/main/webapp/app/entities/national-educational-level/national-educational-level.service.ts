import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INationalEducationalLevel } from 'app/shared/model/national-educational-level.model';

type EntityResponseType = HttpResponse<INationalEducationalLevel>;
type EntityArrayResponseType = HttpResponse<INationalEducationalLevel[]>;

@Injectable({ providedIn: 'root' })
export class NationalEducationalLevelService {
  public resourceUrl = SERVER_API_URL + 'api/national-educational-levels';

  constructor(protected http: HttpClient) {}

  create(nationalEducationalLevel: INationalEducationalLevel): Observable<EntityResponseType> {
    return this.http.post<INationalEducationalLevel>(this.resourceUrl, nationalEducationalLevel, { observe: 'response' });
  }

  update(nationalEducationalLevel: INationalEducationalLevel): Observable<EntityResponseType> {
    return this.http.put<INationalEducationalLevel>(this.resourceUrl, nationalEducationalLevel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INationalEducationalLevel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INationalEducationalLevel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
