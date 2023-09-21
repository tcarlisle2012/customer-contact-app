import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CustomerContactDetailComponent } from './customer-contact-detail.component';

describe('CustomerContact Management Detail Component', () => {
  let comp: CustomerContactDetailComponent;
  let fixture: ComponentFixture<CustomerContactDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CustomerContactDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ customerContact: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CustomerContactDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CustomerContactDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load customerContact on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.customerContact).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
