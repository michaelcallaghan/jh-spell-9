import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRuleException } from 'app/shared/model/rule-exception.model';

type EntityResponseType = HttpResponse<IRuleException>;
type EntityArrayResponseType = HttpResponse<IRuleException[]>;

@Injectable({ providedIn: 'root' })
export class RuleExceptionService {
  public resourceUrl = SERVER_API_URL + 'api/rule-exceptions';

  constructor(protected http: HttpClient) {}

  create(ruleException: IRuleException): Observable<EntityResponseType> {
    return this.http.post<IRuleException>(this.resourceUrl, ruleException, { observe: 'response' });
  }

  update(ruleException: IRuleException): Observable<EntityResponseType> {
    return this.http.put<IRuleException>(this.resourceUrl, ruleException, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRuleException>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRuleException[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
