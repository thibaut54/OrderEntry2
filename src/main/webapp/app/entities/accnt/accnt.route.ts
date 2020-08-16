import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAccnt, Accnt } from 'app/shared/model/accnt.model';
import { AccntService } from './accnt.service';
import { AccntComponent } from './accnt.component';
import { AccntDetailComponent } from './accnt-detail.component';
import { AccntUpdateComponent } from './accnt-update.component';

@Injectable({ providedIn: 'root' })
export class AccntResolve implements Resolve<IAccnt> {
  constructor(private service: AccntService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccnt> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((accnt: HttpResponse<Accnt>) => {
          if (accnt.body) {
            return of(accnt.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Accnt());
  }
}

export const accntRoute: Routes = [
  {
    path: '',
    component: AccntComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'orderEntry2App.accnt.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AccntDetailComponent,
    resolve: {
      accnt: AccntResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'orderEntry2App.accnt.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AccntUpdateComponent,
    resolve: {
      accnt: AccntResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'orderEntry2App.accnt.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AccntUpdateComponent,
    resolve: {
      accnt: AccntResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'orderEntry2App.accnt.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
