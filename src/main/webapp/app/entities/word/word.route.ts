import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWord, Word } from 'app/shared/model/word.model';
import { WordService } from './word.service';
import { WordComponent } from './word.component';
import { WordDetailComponent } from './word-detail.component';
import { WordUpdateComponent } from './word-update.component';

@Injectable({ providedIn: 'root' })
export class WordResolve implements Resolve<IWord> {
  constructor(private service: WordService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWord> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((word: HttpResponse<Word>) => {
          if (word.body) {
            return of(word.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Word());
  }
}

export const wordRoute: Routes = [
  {
    path: '',
    component: WordComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Words',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WordDetailComponent,
    resolve: {
      word: WordResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Words',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WordUpdateComponent,
    resolve: {
      word: WordResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Words',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WordUpdateComponent,
    resolve: {
      word: WordResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Words',
    },
    canActivate: [UserRouteAccessService],
  },
];
