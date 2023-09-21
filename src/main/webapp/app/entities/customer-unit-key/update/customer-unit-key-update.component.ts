import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CustomerUnitKeyFormService, CustomerUnitKeyFormGroup } from './customer-unit-key-form.service';
import { ICustomerUnitKey } from '../customer-unit-key.model';
import { CustomerUnitKeyService } from '../service/customer-unit-key.service';

@Component({
  selector: 'jhi-customer-unit-key-update',
  templateUrl: './customer-unit-key-update.component.html',
})
export class CustomerUnitKeyUpdateComponent implements OnInit {
  isSaving = false;
  customerUnitKey: ICustomerUnitKey | null = null;

  editForm: CustomerUnitKeyFormGroup = this.customerUnitKeyFormService.createCustomerUnitKeyFormGroup();

  constructor(
    protected customerUnitKeyService: CustomerUnitKeyService,
    protected customerUnitKeyFormService: CustomerUnitKeyFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerUnitKey }) => {
      this.customerUnitKey = customerUnitKey;
      if (customerUnitKey) {
        this.updateForm(customerUnitKey);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customerUnitKey = this.customerUnitKeyFormService.getCustomerUnitKey(this.editForm);
    if (customerUnitKey.id !== null) {
      this.subscribeToSaveResponse(this.customerUnitKeyService.update(customerUnitKey));
    } else {
      this.subscribeToSaveResponse(this.customerUnitKeyService.create(customerUnitKey));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerUnitKey>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(customerUnitKey: ICustomerUnitKey): void {
    this.customerUnitKey = customerUnitKey;
    this.customerUnitKeyFormService.resetForm(this.editForm, customerUnitKey);
  }
}
