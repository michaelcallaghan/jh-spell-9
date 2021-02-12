import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRule, Rule } from 'app/shared/model/rule.model';
import { RuleService } from './rule.service';
import { RuleComponent } from './rule.component';
import { RuleDetailComponent } from './rule-detail.component';
import { RuleUpdateComponent } from './rule-update.component';

@Injectable({ providedIn: 'root' })
export class RuleResolve implements Resolve<IRule> {
  constructor(private service: RuleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRule> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((rule: HttpResponse<Rule>) => {
          if (rule.body) {
            return of(rule.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Rule());
  }
}

export const ruleRoute: Routes = [
  {
    path: '',
    component: RuleComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Rules',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RuleDetailComponent,
    resolve: {
      rule: RuleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Rules',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RuleUpdateComponent,
    resolve: {
      rule: RuleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Rules',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RuleUpdateComponent,
    resolve: {
      rule: RuleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Rules',
    },
    canActivate: [UserRouteAccessService],
  },
];
