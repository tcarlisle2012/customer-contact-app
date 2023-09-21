import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../customer-unit-key.test-samples';

import { CustomerUnitKeyFormService } from './customer-unit-key-form.service';

describe('CustomerUnitKey Form Service', () => {
  let service: CustomerUnitKeyFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CustomerUnitKeyFormService);
  });

  describe('Service methods', () => {
    describe('createCustomerUnitKeyFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCustomerUnitKeyFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            customerNumber: expect.any(Object),
            salesOrganization: expect.any(Object),
            distributionChannel: expect.any(Object),
            division: expect.any(Object),
          })
        );
      });

      it('passing ICustomerUnitKey should create a new form with FormGroup', () => {
        const formGroup = service.createCustomerUnitKeyFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            customerNumber: expect.any(Object),
            salesOrganization: expect.any(Object),
            distributionChannel: expect.any(Object),
            division: expect.any(Object),
          })
        );
      });
    });

    describe('getCustomerUnitKey', () => {
      it('should return NewCustomerUnitKey for default CustomerUnitKey initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCustomerUnitKeyFormGroup(sampleWithNewData);

        const customerUnitKey = service.getCustomerUnitKey(formGroup) as any;

        expect(customerUnitKey).toMatchObject(sampleWithNewData);
      });

      it('should return NewCustomerUnitKey for empty CustomerUnitKey initial value', () => {
        const formGroup = service.createCustomerUnitKeyFormGroup();

        const customerUnitKey = service.getCustomerUnitKey(formGroup) as any;

        expect(customerUnitKey).toMatchObject({});
      });

      it('should return ICustomerUnitKey', () => {
        const formGroup = service.createCustomerUnitKeyFormGroup(sampleWithRequiredData);

        const customerUnitKey = service.getCustomerUnitKey(formGroup) as any;

        expect(customerUnitKey).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICustomerUnitKey should not enable id FormControl', () => {
        const formGroup = service.createCustomerUnitKeyFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCustomerUnitKey should disable id FormControl', () => {
        const formGroup = service.createCustomerUnitKeyFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
