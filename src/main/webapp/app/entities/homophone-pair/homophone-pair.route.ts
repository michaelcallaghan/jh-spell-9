import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IHomophonePair, HomophonePair } from 'app/shared/model/homophone-pair.model';
import { HomophonePairService } from './homophone-pair.service';
import { HomophonePairComponent } from './homophone-pair.component';
import { HomophonePairDetailComponent } from './homophone-pair-detail.component';
import { HomophonePairUpdateComponent } from './homophone-pair-update.component';

@Injectable({ providedIn: 'root' })
export class HomophonePairResolve implements Resolve<IHomophonePair> {
  constructor(private service: HomophonePairService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHomophonePair> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((homophonePair: HttpResponse<HomophonePair>) => {
          if (homophonePair.body) {
            return of(homophonePair.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HomophonePair());
  }
}

export const homophonePairRoute: Routes = [
  {
    path: '',
    component: HomophonePairComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'HomophonePairs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HomophonePairDetailComponent,
    resolve: {
      homophonePair: HomophonePairResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'HomophonePairs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HomophonePairUpdateComponent,
    resolve: {
      homophonePair: HomophonePairResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'HomophonePairs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HomophonePairUpdateComponent,
    resolve: {
      homophonePair: HomophonePairResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'HomophonePairs',
    },
    canActivate: [UserRouteAccessService],
  },
];
