import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPgclo, Pgclo } from 'app/shared/model/pgclo.model';
import { PgcloService } from './pgclo.service';
import { PgcloComponent } from './pgclo.component';
import { PgcloDetailComponent } from './pgclo-detail.component';
import { PgcloUpdateComponent } from './pgclo-update.component';

@Injectable({ providedIn: 'root' })
export class PgcloResolve implements Resolve<IPgclo> {
  constructor(private service: PgcloService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPgclo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pgclo: HttpResponse<Pgclo>) => {
          if (pgclo.body) {
            return of(pgclo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Pgclo());
  }
}

export const pgcloRoute: Routes = [
  {
    path: '',
    component: PgcloComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Pgclos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PgcloDetailComponent,
    resolve: {
      pgclo: PgcloResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Pgclos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PgcloUpdateComponent,
    resolve: {
      pgclo: PgcloResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Pgclos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PgcloUpdateComponent,
    resolve: {
      pgclo: PgcloResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Pgclos',
    },
    canActivate: [UserRouteAccessService],
  },
];
