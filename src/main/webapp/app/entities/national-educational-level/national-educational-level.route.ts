import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INationalEducationalLevel, NationalEducationalLevel } from 'app/shared/model/national-educational-level.model';
import { NationalEducationalLevelService } from './national-educational-level.service';
import { NationalEducationalLevelComponent } from './national-educational-level.component';
import { NationalEducationalLevelDetailComponent } from './national-educational-level-detail.component';
import { NationalEducationalLevelUpdateComponent } from './national-educational-level-update.component';

@Injectable({ providedIn: 'root' })
export class NationalEducationalLevelResolve implements Resolve<INationalEducationalLevel> {
  constructor(private service: NationalEducationalLevelService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INationalEducationalLevel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((nationalEducationalLevel: HttpResponse<NationalEducationalLevel>) => {
          if (nationalEducationalLevel.body) {
            return of(nationalEducationalLevel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NationalEducationalLevel());
  }
}

export const nationalEducationalLevelRoute: Routes = [
  {
    path: '',
    component: NationalEducationalLevelComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'NationalEducationalLevels',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NationalEducationalLevelDetailComponent,
    resolve: {
      nationalEducationalLevel: NationalEducationalLevelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'NationalEducationalLevels',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NationalEducationalLevelUpdateComponent,
    resolve: {
      nationalEducationalLevel: NationalEducationalLevelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'NationalEducationalLevels',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NationalEducationalLevelUpdateComponent,
    resolve: {
      nationalEducationalLevel: NationalEducationalLevelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'NationalEducationalLevels',
    },
    canActivate: [UserRouteAccessService],
  },
];
