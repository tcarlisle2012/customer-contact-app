import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICustomerUnitKey } from '../customer-unit-key.model';
import { CustomerUnitKeyService } from '../service/customer-unit-key.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './customer-unit-key-delete-dialog.component.html',
})
export class CustomerUnitKeyDeleteDialogComponent {
  customerUnitKey?: ICustomerUnitKey;

  constructor(protected customerUnitKeyService: CustomerUnitKeyService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.customerUnitKeyService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
