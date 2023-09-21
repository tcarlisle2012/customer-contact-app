import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../customer-contact.test-samples';

import { CustomerContactFormService } from './customer-contact-form.service';

describe('CustomerContact Form Service', () => {
  let service: CustomerContactFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CustomerContactFormService);
  });

  describe('Service methods', () => {
    describe('createCustomerContactFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCustomerContactFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            displayName: expect.any(Object),
            email: expect.any(Object),
            phoneNumber: expect.any(Object),
            customerUnitKey: expect.any(Object),
          })
        );
      });

      it('passing ICustomerContact should create a new form with FormGroup', () => {
        const formGroup = service.createCustomerContactFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            displayName: expect.any(Object),
            email: expect.any(Object),
            phoneNumber: expect.any(Object),
            customerUnitKey: expect.any(Object),
          })
        );
      });
    });

    describe('getCustomerContact', () => {
      it('should return NewCustomerContact for default CustomerContact initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCustomerContactFormGroup(sampleWithNewData);

        const customerContact = service.getCustomerContact(formGroup) as any;

        expect(customerContact).toMatchObject(sampleWithNewData);
      });

      it('should return NewCustomerContact for empty CustomerContact initial value', () => {
        const formGroup = service.createCustomerContactFormGroup();

        const customerContact = service.getCustomerContact(formGroup) as any;

        expect(customerContact).toMatchObject({});
      });

      it('should return ICustomerContact', () => {
        const formGroup = service.createCustomerContactFormGroup(sampleWithRequiredData);

        const customerContact = service.getCustomerContact(formGroup) as any;

        expect(customerContact).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICustomerContact should not enable id FormControl', () => {
        const formGroup = service.createCustomerContactFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCustomerContact should disable id FormControl', () => {
        const formGroup = service.createCustomerContactFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
