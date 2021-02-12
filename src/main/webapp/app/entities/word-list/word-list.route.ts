import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWordList, WordList } from 'app/shared/model/word-list.model';
import { WordListService } from './word-list.service';
import { WordListComponent } from './word-list.component';
import { WordListDetailComponent } from './word-list-detail.component';
import { WordListUpdateComponent } from './word-list-update.component';

@Injectable({ providedIn: 'root' })
export class WordListResolve implements Resolve<IWordList> {
  constructor(private service: WordListService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWordList> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((wordList: HttpResponse<WordList>) => {
          if (wordList.body) {
            return of(wordList.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WordList());
  }
}

export const wordListRoute: Routes = [
  {
    path: '',
    component: WordListComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'WordLists',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WordListDetailComponent,
    resolve: {
      wordList: WordListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'WordLists',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WordListUpdateComponent,
    resolve: {
      wordList: WordListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'WordLists',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WordListUpdateComponent,
    resolve: {
      wordList: WordListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'WordLists',
    },
    canActivate: [UserRouteAccessService],
  },
];
