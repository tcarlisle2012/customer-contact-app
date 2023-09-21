import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CustomerContactComponent } from './list/customer-contact.component';
import { CustomerContactDetailComponent } from './detail/customer-contact-detail.component';
import { CustomerContactUpdateComponent } from './update/customer-contact-update.component';
import { CustomerContactDeleteDialogComponent } from './delete/customer-contact-delete-dialog.component';
import { CustomerContactRoutingModule } from './route/customer-contact-routing.module';

@NgModule({
  imports: [SharedModule, CustomerContactRoutingModule],
  declarations: [
    CustomerContactComponent,
    CustomerContactDetailComponent,
    CustomerContactUpdateComponent,
    CustomerContactDeleteDialogComponent,
  ],
})
export class CustomerContactModule {}
