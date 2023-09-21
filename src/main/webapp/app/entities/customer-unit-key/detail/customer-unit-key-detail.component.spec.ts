import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CustomerUnitKeyDetailComponent } from './customer-unit-key-detail.component';

describe('CustomerUnitKey Management Detail Component', () => {
  let comp: CustomerUnitKeyDetailComponent;
  let fixture: ComponentFixture<CustomerUnitKeyDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CustomerUnitKeyDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ customerUnitKey: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CustomerUnitKeyDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CustomerUnitKeyDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load customerUnitKey on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.customerUnitKey).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
