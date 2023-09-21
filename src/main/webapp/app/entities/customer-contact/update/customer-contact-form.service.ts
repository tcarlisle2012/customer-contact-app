import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICustomerContact, NewCustomerContact } from '../customer-contact.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICustomerContact for edit and NewCustomerContactFormGroupInput for create.
 */
type CustomerContactFormGroupInput = ICustomerContact | PartialWithRequiredKeyOf<NewCustomerContact>;

type CustomerContactFormDefaults = Pick<NewCustomerContact, 'id'>;

type CustomerContactFormGroupContent = {
  id: FormControl<ICustomerContact['id'] | NewCustomerContact['id']>;
  firstName: FormControl<ICustomerContact['firstName']>;
  lastName: FormControl<ICustomerContact['lastName']>;
  displayName: FormControl<ICustomerContact['displayName']>;
  email: FormControl<ICustomerContact['email']>;
  phoneNumber: FormControl<ICustomerContact['phoneNumber']>;
  customerUnitKey: FormControl<ICustomerContact['customerUnitKey']>;
};

export type CustomerContactFormGroup = FormGroup<CustomerContactFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CustomerContactFormService {
  createCustomerContactFormGroup(customerContact: CustomerContactFormGroupInput = { id: null }): CustomerContactFormGroup {
    const customerContactRawValue = {
      ...this.getFormDefaults(),
      ...customerContact,
    };
    return new FormGroup<CustomerContactFormGroupContent>({
      id: new FormControl(
        { value: customerContactRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      firstName: new FormControl(customerContactRawValue.firstName, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
      }),
      lastName: new FormControl(customerContactRawValue.lastName, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
      }),
      displayName: new FormControl(customerContactRawValue.displayName, {
        validators: [Validators.required, Validators.minLength(1), Validators.maxLength(150)],
      }),
      email: new FormControl(customerContactRawValue.email, {
        validators: [
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(254),
          Validators.pattern('^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$'),
        ],
      }),
      phoneNumber: new FormControl(customerContactRawValue.phoneNumber, {
        validators: [Validators.required, Validators.minLength(10), Validators.maxLength(10)],
      }),
      customerUnitKey: new FormControl(customerContactRawValue.customerUnitKey, {
        validators: [Validators.required],
      }),
    });
  }

  getCustomerContact(form: CustomerContactFormGroup): ICustomerContact | NewCustomerContact {
    return form.getRawValue() as ICustomerContact | NewCustomerContact;
  }

  resetForm(form: CustomerContactFormGroup, customerContact: CustomerContactFormGroupInput): void {
    const customerContactRawValue = { ...this.getFormDefaults(), ...customerContact };
    form.reset(
      {
        ...customerContactRawValue,
        id: { value: customerContactRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CustomerContactFormDefaults {
    return {
      id: null,
    };
  }
}
