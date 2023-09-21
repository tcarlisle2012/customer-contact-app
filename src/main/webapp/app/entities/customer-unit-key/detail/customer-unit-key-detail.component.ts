import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICustomerUnitKey } from '../customer-unit-key.model';

@Component({
  selector: 'jhi-customer-unit-key-detail',
  templateUrl: './customer-unit-key-detail.component.html',
})
export class CustomerUnitKeyDetailComponent implements OnInit {
  customerUnitKey: ICustomerUnitKey | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerUnitKey }) => {
      this.customerUnitKey = customerUnitKey;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
