import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRuleExample, RuleExample } from 'app/shared/model/rule-example.model';
import { RuleExampleService } from './rule-example.service';
import { RuleExampleComponent } from './rule-example.component';
import { RuleExampleDetailComponent } from './rule-example-detail.component';
import { RuleExampleUpdateComponent } from './rule-example-update.component';

@Injectable({ providedIn: 'root' })
export class RuleExampleResolve implements Resolve<IRuleExample> {
  constructor(private service: RuleExampleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRuleExample> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ruleExample: HttpResponse<RuleExample>) => {
          if (ruleExample.body) {
            return of(ruleExample.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RuleExample());
  }
}

export const ruleExampleRoute: Routes = [
  {
    path: '',
    component: RuleExampleComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'RuleExamples',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RuleExampleDetailComponent,
    resolve: {
      ruleExample: RuleExampleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'RuleExamples',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RuleExampleUpdateComponent,
    resolve: {
      ruleExample: RuleExampleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'RuleExamples',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RuleExampleUpdateComponent,
    resolve: {
      ruleExample: RuleExampleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'RuleExamples',
    },
    canActivate: [UserRouteAccessService],
  },
];
