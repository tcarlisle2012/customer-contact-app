import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CustomerUnitKeyFormService } from './customer-unit-key-form.service';
import { CustomerUnitKeyService } from '../service/customer-unit-key.service';
import { ICustomerUnitKey } from '../customer-unit-key.model';

import { CustomerUnitKeyUpdateComponent } from './customer-unit-key-update.component';

describe('CustomerUnitKey Management Update Component', () => {
  let comp: CustomerUnitKeyUpdateComponent;
  let fixture: ComponentFixture<CustomerUnitKeyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let customerUnitKeyFormService: CustomerUnitKeyFormService;
  let customerUnitKeyService: CustomerUnitKeyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CustomerUnitKeyUpdateComponent],
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
      .overrideTemplate(CustomerUnitKeyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CustomerUnitKeyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    customerUnitKeyFormService = TestBed.inject(CustomerUnitKeyFormService);
    customerUnitKeyService = TestBed.inject(CustomerUnitKeyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const customerUnitKey: ICustomerUnitKey = { id: 456 };

      activatedRoute.data = of({ customerUnitKey });
      comp.ngOnInit();

      expect(comp.customerUnitKey).toEqual(customerUnitKey);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICustomerUnitKey>>();
      const customerUnitKey = { id: 123 };
      jest.spyOn(customerUnitKeyFormService, 'getCustomerUnitKey').mockReturnValue(customerUnitKey);
      jest.spyOn(customerUnitKeyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ customerUnitKey });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: customerUnitKey }));
      saveSubject.complete();

      // THEN
      expect(customerUnitKeyFormService.getCustomerUnitKey).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(customerUnitKeyService.update).toHaveBeenCalledWith(expect.objectContaining(customerUnitKey));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICustomerUnitKey>>();
      const customerUnitKey = { id: 123 };
      jest.spyOn(customerUnitKeyFormService, 'getCustomerUnitKey').mockReturnValue({ id: null });
      jest.spyOn(customerUnitKeyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ customerUnitKey: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: customerUnitKey }));
      saveSubject.complete();

      // THEN
      expect(customerUnitKeyFormService.getCustomerUnitKey).toHaveBeenCalled();
      expect(customerUnitKeyService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICustomerUnitKey>>();
      const customerUnitKey = { id: 123 };
      jest.spyOn(customerUnitKeyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ customerUnitKey });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(customerUnitKeyService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
