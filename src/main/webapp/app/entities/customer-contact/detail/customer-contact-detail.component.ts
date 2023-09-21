import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustomerContact } from '../customer-contact.model';

@Component({
  selector: 'jhi-customer-contact-detail',
  templateUrl: './customer-contact-detail.component.html',
})
export class CustomerContactDetailComponent implements OnInit {
  customerContact: ICustomerContact | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerContact }) => {
      this.customerContact = customerContact;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
