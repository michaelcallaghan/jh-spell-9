import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRuleException, RuleException } from 'app/shared/model/rule-exception.model';
import { RuleExceptionService } from './rule-exception.service';
import { RuleExceptionComponent } from './rule-exception.component';
import { RuleExceptionDetailComponent } from './rule-exception-detail.component';
import { RuleExceptionUpdateComponent } from './rule-exception-update.component';

@Injectable({ providedIn: 'root' })
export class RuleExceptionResolve implements Resolve<IRuleException> {
  constructor(private service: RuleExceptionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRuleException> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ruleException: HttpResponse<RuleException>) => {
          if (ruleException.body) {
            return of(ruleException.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RuleException());
  }
}

export const ruleExceptionRoute: Routes = [
  {
    path: '',
    component: RuleExceptionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'RuleExceptions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RuleExceptionDetailComponent,
    resolve: {
      ruleException: RuleExceptionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'RuleExceptions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RuleExceptionUpdateComponent,
    resolve: {
      ruleException: RuleExceptionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'RuleExceptions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RuleExceptionUpdateComponent,
    resolve: {
      ruleException: RuleExceptionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'RuleExceptions',
    },
    canActivate: [UserRouteAccessService],
  },
];
