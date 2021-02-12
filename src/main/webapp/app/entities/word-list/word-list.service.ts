import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWordList } from 'app/shared/model/word-list.model';

type EntityResponseType = HttpResponse<IWordList>;
type EntityArrayResponseType = HttpResponse<IWordList[]>;

@Injectable({ providedIn: 'root' })
export class WordListService {
  public resourceUrl = SERVER_API_URL + 'api/word-lists';

  constructor(protected http: HttpClient) {}

  create(wordList: IWordList): Observable<EntityResponseType> {
    return this.http.post<IWordList>(this.resourceUrl, wordList, { observe: 'response' });
  }

  update(wordList: IWordList): Observable<EntityResponseType> {
    return this.http.put<IWordList>(this.resourceUrl, wordList, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWordList>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWordList[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
