import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWordInList } from 'app/shared/model/word-in-list.model';

type EntityResponseType = HttpResponse<IWordInList>;
type EntityArrayResponseType = HttpResponse<IWordInList[]>;

@Injectable({ providedIn: 'root' })
export class WordInListService {
  public resourceUrl = SERVER_API_URL + 'api/word-in-lists';

  constructor(protected http: HttpClient) {}

  create(wordInList: IWordInList): Observable<EntityResponseType> {
    return this.http.post<IWordInList>(this.resourceUrl, wordInList, { observe: 'response' });
  }

  update(wordInList: IWordInList): Observable<EntityResponseType> {
    return this.http.put<IWordInList>(this.resourceUrl, wordInList, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWordInList>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWordInList[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
