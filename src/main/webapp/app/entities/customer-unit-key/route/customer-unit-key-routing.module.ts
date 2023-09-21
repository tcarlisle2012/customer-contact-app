import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CustomerUnitKeyComponent } from '../list/customer-unit-key.component';
import { CustomerUnitKeyDetailComponent } from '../detail/customer-unit-key-detail.component';
import { CustomerUnitKeyUpdateComponent } from '../update/customer-unit-key-update.component';
import { CustomerUnitKeyRoutingResolveService } from './customer-unit-key-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const customerUnitKeyRoute: Routes = [
  {
    path: '',
    component: CustomerUnitKeyComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustomerUnitKeyDetailComponent,
    resolve: {
      customerUnitKey: CustomerUnitKeyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustomerUnitKeyUpdateComponent,
    resolve: {
      customerUnitKey: CustomerUnitKeyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustomerUnitKeyUpdateComponent,
    resolve: {
      customerUnitKey: CustomerUnitKeyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(customerUnitKeyRoute)],
  exports: [RouterModule],
})
export class CustomerUnitKeyRoutingModule {}
