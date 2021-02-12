import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPgc, Pgc } from 'app/shared/model/pgc.model';
import { PgcService } from './pgc.service';
import { PgcComponent } from './pgc.component';
import { PgcDetailComponent } from './pgc-detail.component';
import { PgcUpdateComponent } from './pgc-update.component';

@Injectable({ providedIn: 'root' })
export class PgcResolve implements Resolve<IPgc> {
  constructor(private service: PgcService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPgc> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pgc: HttpResponse<Pgc>) => {
          if (pgc.body) {
            return of(pgc.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Pgc());
  }
}

export const pgcRoute: Routes = [
  {
    path: '',
    component: PgcComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Pgcs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PgcDetailComponent,
    resolve: {
      pgc: PgcResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Pgcs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PgcUpdateComponent,
    resolve: {
      pgc: PgcResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Pgcs',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PgcUpdateComponent,
    resolve: {
      pgc: PgcResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Pgcs',
    },
    canActivate: [UserRouteAccessService],
  },
];
