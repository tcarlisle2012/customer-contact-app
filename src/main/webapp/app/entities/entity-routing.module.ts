import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'customer-unit-key',
        data: { pageTitle: 'customerContactApp.customerUnitKey.home.title' },
        loadChildren: () => import('./customer-unit-key/customer-unit-key.module').then(m => m.CustomerUnitKeyModule),
      },
      {
        path: 'customer-contact',
        data: { pageTitle: 'customerContactApp.customerContact.home.title' },
        loadChildren: () => import('./customer-contact/customer-contact.module').then(m => m.CustomerContactModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
