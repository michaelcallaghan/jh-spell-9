import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWord } from 'app/shared/model/word.model';

type EntityResponseType = HttpResponse<IWord>;
type EntityArrayResponseType = HttpResponse<IWord[]>;

@Injectable({ providedIn: 'root' })
export class WordService {
  public resourceUrl = SERVER_API_URL + 'api/words';

  constructor(protected http: HttpClient) {}

  create(word: IWord): Observable<EntityResponseType> {
    return this.http.post<IWord>(this.resourceUrl, word, { observe: 'response' });
  }

  update(word: IWord): Observable<EntityResponseType> {
    return this.http.put<IWord>(this.resourceUrl, word, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWord>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWord[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
