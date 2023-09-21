import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CustomerContactFormService } from './customer-contact-form.service';
import { CustomerContactService } from '../service/customer-contact.service';
import { ICustomerContact } from '../customer-contact.model';
import { ICustomerUnitKey } from 'app/entities/customer-unit-key/customer-unit-key.model';
import { CustomerUnitKeyService } from 'app/entities/customer-unit-key/service/customer-unit-key.service';

import { CustomerContactUpdateComponent } from './customer-contact-update.component';

describe('CustomerContact Management Update Component', () => {
  let comp: CustomerContactUpdateComponent;
  let fixture: ComponentFixture<CustomerContactUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let customerContactFormService: CustomerContactFormService;
  let customerContactService: CustomerContactService;
  let customerUnitKeyService: CustomerUnitKeyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CustomerContactUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CustomerContactUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CustomerContactUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    customerContactFormService = TestBed.inject(CustomerContactFormService);
    customerContactService = TestBed.inject(CustomerContactService);
    customerUnitKeyService = TestBed.inject(CustomerUnitKeyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call CustomerUnitKey query and add missing value', () => {
      const customerContact: ICustomerContact = { id: 456 };
      const customerUnitKey: ICustomerUnitKey = { id: 42193 };
      customerContact.customerUnitKey = customerUnitKey;

      const customerUnitKeyCollection: ICustomerUnitKey[] = [{ id: 11715 }];
      jest.spyOn(customerUnitKeyService, 'query').mockReturnValue(of(new HttpResponse({ body: customerUnitKeyCollection })));
      const additionalCustomerUnitKeys = [customerUnitKey];
      const expectedCollection: ICustomerUnitKey[] = [...additionalCustomerUnitKeys, ...customerUnitKeyCollection];
      jest.spyOn(customerUnitKeyService, 'addCustomerUnitKeyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ customerContact });
      comp.ngOnInit();

      expect(customerUnitKeyService.query).toHaveBeenCalled();
      expect(customerUnitKeyService.addCustomerUnitKeyToCollectionIfMissing).toHaveBeenCalledWith(
        customerUnitKeyCollection,
        ...additionalCustomerUnitKeys.map(expect.objectContaining)
      );
      expect(comp.customerUnitKeysSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const customerContact: ICustomerContact = { id: 456 };
      const customerUnitKey: ICustomerUnitKey = { id: 35066 };
      customerContact.customerUnitKey = customerUnitKey;

      activatedRoute.data = of({ customerContact });
      comp.ngOnInit();

      expect(comp.customerUnitKeysSharedCollection).toContain(customerUnitKey);
      expect(comp.customerContact).toEqual(customerContact);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICustomerContact>>();
      const customerContact = { id: 123 };
      jest.spyOn(customerContactFormService, 'getCustomerContact').mockReturnValue(customerContact);
      jest.spyOn(customerContactService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ customerContact });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: customerContact }));
      saveSubject.complete();

      // THEN
      expect(customerContactFormService.getCustomerContact).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(customerContactService.update).toHaveBeenCalledWith(expect.objectContaining(customerContact));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICustomerContact>>();
      const customerContact = { id: 123 };
      jest.spyOn(customerContactFormService, 'getCustomerContact').mockReturnValue({ id: null });
      jest.spyOn(customerContactService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ customerContact: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: customerContact }));
      saveSubject.complete();

      // THEN
      expect(customerContactFormService.getCustomerContact).toHaveBeenCalled();
      expect(customerContactService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICustomerContact>>();
      const customerContact = { id: 123 };
      jest.spyOn(customerContactService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ customerContact });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(customerContactService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCustomerUnitKey', () => {
      it('Should forward to customerUnitKeyService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(customerUnitKeyService, 'compareCustomerUnitKey');
        comp.compareCustomerUnitKey(entity, entity2);
        expect(customerUnitKeyService.compareCustomerUnitKey).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
