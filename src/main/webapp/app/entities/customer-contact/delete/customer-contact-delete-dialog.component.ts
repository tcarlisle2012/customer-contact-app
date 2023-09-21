import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICustomerContact } from '../customer-contact.model';
import { CustomerContactService } from '../service/customer-contact.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './customer-contact-delete-dialog.component.html',
})
export class CustomerContactDeleteDialogComponent {
  customerContact?: ICustomerContact;

  constructor(protected customerContactService: CustomerContactService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.customerContactService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
