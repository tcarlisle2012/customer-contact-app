import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICustomerUnitKey, NewCustomerUnitKey } from '../customer-unit-key.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICustomerUnitKey for edit and NewCustomerUnitKeyFormGroupInput for create.
 */
type CustomerUnitKeyFormGroupInput = ICustomerUnitKey | PartialWithRequiredKeyOf<NewCustomerUnitKey>;

type CustomerUnitKeyFormDefaults = Pick<NewCustomerUnitKey, 'id'>;

type CustomerUnitKeyFormGroupContent = {
  id: FormControl<ICustomerUnitKey['id'] | NewCustomerUnitKey['id']>;
  customerNumber: FormControl<ICustomerUnitKey['customerNumber']>;
  salesOrganization: FormControl<ICustomerUnitKey['salesOrganization']>;
  distributionChannel: FormControl<ICustomerUnitKey['distributionChannel']>;
  division: FormControl<ICustomerUnitKey['division']>;
};

export type CustomerUnitKeyFormGroup = FormGroup<CustomerUnitKeyFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CustomerUnitKeyFormService {
  createCustomerUnitKeyFormGroup(customerUnitKey: CustomerUnitKeyFormGroupInput = { id: null }): CustomerUnitKeyFormGroup {
    const customerUnitKeyRawValue = {
      ...this.getFormDefaults(),
      ...customerUnitKey,
    };
    return new FormGroup<CustomerUnitKeyFormGroupContent>({
      id: new FormControl(
        { value: customerUnitKeyRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      customerNumber: new FormControl(customerUnitKeyRawValue.customerNumber, {
        validators: [Validators.required, Validators.maxLength(16)],
      }),
      salesOrganization: new FormControl(customerUnitKeyRawValue.salesOrganization, {
        validators: [Validators.required, Validators.maxLength(16)],
      }),
      distributionChannel: new FormControl(customerUnitKeyRawValue.distributionChannel, {
        validators: [Validators.required, Validators.maxLength(16)],
      }),
      division: new FormControl(customerUnitKeyRawValue.division, {
        validators: [Validators.required, Validators.maxLength(16)],
      }),
    });
  }

  getCustomerUnitKey(form: CustomerUnitKeyFormGroup): ICustomerUnitKey | NewCustomerUnitKey {
    return form.getRawValue() as ICustomerUnitKey | NewCustomerUnitKey;
  }

  resetForm(form: CustomerUnitKeyFormGroup, customerUnitKey: CustomerUnitKeyFormGroupInput): void {
    const customerUnitKeyRawValue = { ...this.getFormDefaults(), ...customerUnitKey };
    form.reset(
      {
        ...customerUnitKeyRawValue,
        id: { value: customerUnitKeyRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CustomerUnitKeyFormDefaults {
    return {
      id: null,
    };
  }
}
