import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CustomerUnitKeyComponent } from './list/customer-unit-key.component';
import { CustomerUnitKeyDetailComponent } from './detail/customer-unit-key-detail.component';
import { CustomerUnitKeyUpdateComponent } from './update/customer-unit-key-update.component';
import { CustomerUnitKeyDeleteDialogComponent } from './delete/customer-unit-key-delete-dialog.component';
import { CustomerUnitKeyRoutingModule } from './route/customer-unit-key-routing.module';

@NgModule({
  imports: [SharedModule, CustomerUnitKeyRoutingModule],
  declarations: [
    CustomerUnitKeyComponent,
    CustomerUnitKeyDetailComponent,
    CustomerUnitKeyUpdateComponent,
    CustomerUnitKeyDeleteDialogComponent,
  ],
})
export class CustomerUnitKeyModule {}
