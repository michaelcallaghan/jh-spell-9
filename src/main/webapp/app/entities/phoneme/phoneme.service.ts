import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPhoneme } from 'app/shared/model/phoneme.model';

type EntityResponseType = HttpResponse<IPhoneme>;
type EntityArrayResponseType = HttpResponse<IPhoneme[]>;

@Injectable({ providedIn: 'root' })
export class PhonemeService {
  public resourceUrl = SERVER_API_URL + 'api/phonemes';

  constructor(protected http: HttpClient) {}

  create(phoneme: IPhoneme): Observable<EntityResponseType> {
    return this.http.post<IPhoneme>(this.resourceUrl, phoneme, { observe: 'response' });
  }

  update(phoneme: IPhoneme): Observable<EntityResponseType> {
    return this.http.put<IPhoneme>(this.resourceUrl, phoneme, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPhoneme>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPhoneme[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
