import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICustomerContact } from '../customer-contact.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../customer-contact.test-samples';

import { CustomerContactService } from './customer-contact.service';

const requireRestSample: ICustomerContact = {
  ...sampleWithRequiredData,
};

describe('CustomerContact Service', () => {
  let service: CustomerContactService;
  let httpMock: HttpTestingController;
  let expectedResult: ICustomerContact | ICustomerContact[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CustomerContactService);
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

    it('should create a CustomerContact', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const customerContact = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(customerContact).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CustomerContact', () => {
      const customerContact = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(customerContact).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CustomerContact', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CustomerContact', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CustomerContact', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCustomerContactToCollectionIfMissing', () => {
      it('should add a CustomerContact to an empty array', () => {
        const customerContact: ICustomerContact = sampleWithRequiredData;
        expectedResult = service.addCustomerContactToCollectionIfMissing([], customerContact);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(customerContact);
      });

      it('should not add a CustomerContact to an array that contains it', () => {
        const customerContact: ICustomerContact = sampleWithRequiredData;
        const customerContactCollection: ICustomerContact[] = [
          {
            ...customerContact,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCustomerContactToCollectionIfMissing(customerContactCollection, customerContact);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CustomerContact to an array that doesn't contain it", () => {
        const customerContact: ICustomerContact = sampleWithRequiredData;
        const customerContactCollection: ICustomerContact[] = [sampleWithPartialData];
        expectedResult = service.addCustomerContactToCollectionIfMissing(customerContactCollection, customerContact);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(customerContact);
      });

      it('should add only unique CustomerContact to an array', () => {
        const customerContactArray: ICustomerContact[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const customerContactCollection: ICustomerContact[] = [sampleWithRequiredData];
        expectedResult = service.addCustomerContactToCollectionIfMissing(customerContactCollection, ...customerContactArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const customerContact: ICustomerContact = sampleWithRequiredData;
        const customerContact2: ICustomerContact = sampleWithPartialData;
        expectedResult = service.addCustomerContactToCollectionIfMissing([], customerContact, customerContact2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(customerContact);
        expect(expectedResult).toContain(customerContact2);
      });

      it('should accept null and undefined values', () => {
        const customerContact: ICustomerContact = sampleWithRequiredData;
        expectedResult = service.addCustomerContactToCollectionIfMissing([], null, customerContact, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(customerContact);
      });

      it('should return initial array if no CustomerContact is added', () => {
        const customerContactCollection: ICustomerContact[] = [sampleWithRequiredData];
        expectedResult = service.addCustomerContactToCollectionIfMissing(customerContactCollection, undefined, null);
        expect(expectedResult).toEqual(customerContactCollection);
      });
    });

    describe('compareCustomerContact', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCustomerContact(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCustomerContact(entity1, entity2);
        const compareResult2 = service.compareCustomerContact(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCustomerContact(entity1, entity2);
        const compareResult2 = service.compareCustomerContact(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCustomerContact(entity1, entity2);
        const compareResult2 = service.compareCustomerContact(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
