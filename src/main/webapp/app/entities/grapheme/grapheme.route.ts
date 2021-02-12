import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGrapheme, Grapheme } from 'app/shared/model/grapheme.model';
import { GraphemeService } from './grapheme.service';
import { GraphemeComponent } from './grapheme.component';
import { GraphemeDetailComponent } from './grapheme-detail.component';
import { GraphemeUpdateComponent } from './grapheme-update.component';

@Injectable({ providedIn: 'root' })
export class GraphemeResolve implements Resolve<IGrapheme> {
  constructor(private service: GraphemeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGrapheme> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((grapheme: HttpResponse<Grapheme>) => {
          if (grapheme.body) {
            return of(grapheme.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Grapheme());
  }
}

export const graphemeRoute: Routes = [
  {
    path: '',
    component: GraphemeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Graphemes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GraphemeDetailComponent,
    resolve: {
      grapheme: GraphemeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Graphemes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GraphemeUpdateComponent,
    resolve: {
      grapheme: GraphemeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Graphemes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GraphemeUpdateComponent,
    resolve: {
      grapheme: GraphemeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Graphemes',
    },
    canActivate: [UserRouteAccessService],
  },
];
