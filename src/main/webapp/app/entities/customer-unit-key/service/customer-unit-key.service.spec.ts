import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICustomerUnitKey } from '../customer-unit-key.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../customer-unit-key.test-samples';

import { CustomerUnitKeyService } from './customer-unit-key.service';

const requireRestSample: ICustomerUnitKey = {
  ...sampleWithRequiredData,
};

describe('CustomerUnitKey Service', () => {
  let service: CustomerUnitKeyService;
  let httpMock: HttpTestingController;
  let expectedResult: ICustomerUnitKey | ICustomerUnitKey[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CustomerUnitKeyService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a CustomerUnitKey', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const customerUnitKey = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(customerUnitKey).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CustomerUnitKey', () => {
      const customerUnitKey = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(customerUnitKey).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CustomerUnitKey', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CustomerUnitKey', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CustomerUnitKey', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCustomerUnitKeyToCollectionIfMissing', () => {
      it('should add a CustomerUnitKey to an empty array', () => {
        const customerUnitKey: ICustomerUnitKey = sampleWithRequiredData;
        expectedResult = service.addCustomerUnitKeyToCollectionIfMissing([], customerUnitKey);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(customerUnitKey);
      });

      it('should not add a CustomerUnitKey to an array that contains it', () => {
        const customerUnitKey: ICustomerUnitKey = sampleWithRequiredData;
        const customerUnitKeyCollection: ICustomerUnitKey[] = [
          {
            ...customerUnitKey,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCustomerUnitKeyToCollectionIfMissing(customerUnitKeyCollection, customerUnitKey);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CustomerUnitKey to an array that doesn't contain it", () => {
        const customerUnitKey: ICustomerUnitKey = sampleWithRequiredData;
        const customerUnitKeyCollection: ICustomerUnitKey[] = [sampleWithPartialData];
        expectedResult = service.addCustomerUnitKeyToCollectionIfMissing(customerUnitKeyCollection, customerUnitKey);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(customerUnitKey);
      });

      it('should add only unique CustomerUnitKey to an array', () => {
        const customerUnitKeyArray: ICustomerUnitKey[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const customerUnitKeyCollection: ICustomerUnitKey[] = [sampleWithRequiredData];
        expectedResult = service.addCustomerUnitKeyToCollectionIfMissing(customerUnitKeyCollection, ...customerUnitKeyArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const customerUnitKey: ICustomerUnitKey = sampleWithRequiredData;
        const customerUnitKey2: ICustomerUnitKey = sampleWithPartialData;
        expectedResult = service.addCustomerUnitKeyToCollectionIfMissing([], customerUnitKey, customerUnitKey2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(customerUnitKey);
        expect(expectedResult).toContain(customerUnitKey2);
      });

      it('should accept null and undefined values', () => {
        const customerUnitKey: ICustomerUnitKey = sampleWithRequiredData;
        expectedResult = service.addCustomerUnitKeyToCollectionIfMissing([], null, customerUnitKey, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(customerUnitKey);
      });

      it('should return initial array if no CustomerUnitKey is added', () => {
        const customerUnitKeyCollection: ICustomerUnitKey[] = [sampleWithRequiredData];
        expectedResult = service.addCustomerUnitKeyToCollectionIfMissing(customerUnitKeyCollection, undefined, null);
        expect(expectedResult).toEqual(customerUnitKeyCollection);
      });
    });

    describe('compareCustomerUnitKey', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCustomerUnitKey(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCustomerUnitKey(entity1, entity2);
        const compareResult2 = service.compareCustomerUnitKey(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCustomerUnitKey(entity1, entity2);
        const compareResult2 = service.compareCustomerUnitKey(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCustomerUnitKey(entity1, entity2);
        const compareResult2 = service.compareCustomerUnitKey(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
