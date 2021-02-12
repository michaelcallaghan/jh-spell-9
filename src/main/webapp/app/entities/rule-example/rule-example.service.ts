import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRuleExample } from 'app/shared/model/rule-example.model';

type EntityResponseType = HttpResponse<IRuleExample>;
type EntityArrayResponseType = HttpResponse<IRuleExample[]>;

@Injectable({ providedIn: 'root' })
export class RuleExampleService {
  public resourceUrl = SERVER_API_URL + 'api/rule-examples';

  constructor(protected http: HttpClient) {}

  create(ruleExample: IRuleExample): Observable<EntityResponseType> {
    return this.http.post<IRuleExample>(this.resourceUrl, ruleExample, { observe: 'response' });
  }

  update(ruleExample: IRuleExample): Observable<EntityResponseType> {
    return this.http.put<IRuleExample>(this.resourceUrl, ruleExample, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRuleExample>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRuleExample[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
