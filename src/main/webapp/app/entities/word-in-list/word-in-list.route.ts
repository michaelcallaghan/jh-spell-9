import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWordInList, WordInList } from 'app/shared/model/word-in-list.model';
import { WordInListService } from './word-in-list.service';
import { WordInListComponent } from './word-in-list.component';
import { WordInListDetailComponent } from './word-in-list-detail.component';
import { WordInListUpdateComponent } from './word-in-list-update.component';

@Injectable({ providedIn: 'root' })
export class WordInListResolve implements Resolve<IWordInList> {
  constructor(private service: WordInListService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWordInList> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((wordInList: HttpResponse<WordInList>) => {
          if (wordInList.body) {
            return of(wordInList.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WordInList());
  }
}

export const wordInListRoute: Routes = [
  {
    path: '',
    component: WordInListComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'WordInLists',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WordInListDetailComponent,
    resolve: {
      wordInList: WordInListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'WordInLists',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WordInListUpdateComponent,
    resolve: {
      wordInList: WordInListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'WordInLists',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WordInListUpdateComponent,
    resolve: {
      wordInList: WordInListResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'WordInLists',
    },
    canActivate: [UserRouteAccessService],
  },
];
