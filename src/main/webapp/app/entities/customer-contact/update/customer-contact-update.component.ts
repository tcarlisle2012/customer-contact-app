import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CustomerContactFormService, CustomerContactFormGroup } from './customer-contact-form.service';
import { ICustomerContact } from '../customer-contact.model';
import { CustomerContactService } from '../service/customer-contact.service';
import { ICustomerUnitKey } from 'app/entities/customer-unit-key/customer-unit-key.model';
import { CustomerUnitKeyService } from 'app/entities/customer-unit-key/service/customer-unit-key.service';

@Component({
  selector: 'jhi-customer-contact-update',
  templateUrl: './customer-contact-update.component.html',
})
export class CustomerContactUpdateComponent implements OnInit {
  isSaving = false;
  customerContact: ICustomerContact | null = null;

  customerUnitKeysSharedCollection: ICustomerUnitKey[] = [];

  editForm: CustomerContactFormGroup = this.customerContactFormService.createCustomerContactFormGroup();

  constructor(
    protected customerContactService: CustomerContactService,
    protected customerContactFormService: CustomerContactFormService,
    protected customerUnitKeyService: CustomerUnitKeyService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCustomerUnitKey = (o1: ICustomerUnitKey | null, o2: ICustomerUnitKey | null): boolean =>
    this.customerUnitKeyService.compareCustomerUnitKey(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ customerContact }) => {
      this.customerContact = customerContact;
      if (customerContact) {
        this.updateForm(customerContact);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const customerContact = this.customerContactFormService.getCustomerContact(this.editForm);
    if (customerContact.id !== null) {
      this.subscribeToSaveResponse(this.customerContactService.update(customerContact));
    } else {
      this.subscribeToSaveResponse(this.customerContactService.create(customerContact));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerContact>>): void {
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

  protected updateForm(customerContact: ICustomerContact): void {
    this.customerContact = customerContact;
    this.customerContactFormService.resetForm(this.editForm, customerContact);

    this.customerUnitKeysSharedCollection = this.customerUnitKeyService.addCustomerUnitKeyToCollectionIfMissing<ICustomerUnitKey>(
      this.customerUnitKeysSharedCollection,
      customerContact.customerUnitKey
    );
  }

  protected loadRelationshipsOptions(): void {
    this.customerUnitKeyService
      .query()
      .pipe(map((res: HttpResponse<ICustomerUnitKey[]>) => res.body ?? []))
      .pipe(
        map((customerUnitKeys: ICustomerUnitKey[]) =>
          this.customerUnitKeyService.addCustomerUnitKeyToCollectionIfMissing<ICustomerUnitKey>(
            customerUnitKeys,
            this.customerContact?.customerUnitKey
          )
        )
      )
      .subscribe((customerUnitKeys: ICustomerUnitKey[]) => (this.customerUnitKeysSharedCollection = customerUnitKeys));
  }
}
