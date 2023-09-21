import { ICustomerContact, NewCustomerContact } from './customer-contact.model';

export const sampleWithRequiredData: ICustomerContact = {
  id: 75701,
  firstName: 'Vella',
  lastName: 'Green',
  displayName: 'Pants',
  email: 'TCN@U._MO.8T.y.f67Q',
  phoneNumber: 'AccountXXX',
};

export const sampleWithPartialData: ICustomerContact = {
  id: 37678,
  firstName: 'Kay',
  lastName: 'Wiegand',
  displayName: 'Engineer withdrawal niches',
  email: 'h@xjIq5.jJ7.r5_A.m.oFVeKH.4.lX',
  phoneNumber: 'Group Ando',
};

export const sampleWithFullData: ICustomerContact = {
  id: 68481,
  firstName: 'Seth',
  lastName: 'Stokes',
  displayName: 'bluetooth Automotive',
  email: 'T@B0vVjd.fVJ.t7sz.FQPANF.xW',
  phoneNumber: 'Ergonomic ',
};

export const sampleWithNewData: NewCustomerContact = {
  firstName: 'Alejandra',
  lastName: 'Orn',
  displayName: 'Account',
  email: 'Nk22P@CnyA18.Tr',
  phoneNumber: 'Shilling T',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
