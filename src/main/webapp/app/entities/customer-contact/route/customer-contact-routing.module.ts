import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CustomerContactComponent } from '../list/customer-contact.component';
import { CustomerContactDetailComponent } from '../detail/customer-contact-detail.component';
import { CustomerContactUpdateComponent } from '../update/customer-contact-update.component';
import { CustomerContactRoutingResolveService } from './customer-contact-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const customerContactRoute: Routes = [
  {
    path: '',
    component: CustomerContactComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustomerContactDetailComponent,
    resolve: {
      customerContact: CustomerContactRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustomerContactUpdateComponent,
    resolve: {
      customerContact: CustomerContactRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustomerContactUpdateComponent,
    resolve: {
      customerContact: CustomerContactRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(customerContactRoute)],
  exports: [RouterModule],
})
export class CustomerContactRoutingModule {}
