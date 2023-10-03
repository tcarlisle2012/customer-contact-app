import { ICustomerContact, NewCustomerContact } from './customer-contact.model';

export const sampleWithRequiredData: ICustomerContact = {
  id: 75701,
  firstName: 'Vella',
  middleName: 'approach',
  lastName: 'Wisozk',
  displayName: 'Applications Plaza',
  email: 'V@M.u8Tg.4f6.QlLx',
  phone: '559.992.76',
  department: 'Frozen',
  jobTitle: 'Regional Directives Planner',
};

export const sampleWithPartialData: ICustomerContact = {
  id: 27067,
  firstName: 'Terrill',
  middleName: 'Towels withdrawal',
  lastName: 'Altenwerth',
  displayName: 'Concrete Object-based',
  email: 'L@4.lX.7zqHak.T1Q3.LYNy',
  phone: '931-759-48',
  department: 'Wooden',
  jobTitle: 'Customer Paradigm Representative',
};

export const sampleWithFullData: ICustomerContact = {
  id: 31022,
  firstName: 'Hanna',
  middleName: 'Buckinghamshire systems Fantastic',
  lastName: 'Beier',
  displayName: 'plum Wooden',
  email: 'Q@aZYM.11.b6C',
  phone: '548-907-27',
  department: 'Tasty Books',
  jobTitle: 'Regional Assurance Coordinator',
};

export const sampleWithNewData: NewCustomerContact = {
  firstName: 'Judy',
  middleName: 'Monitored',
  lastName: 'Schinner',
  displayName: 'Outdoors Implementation Peso',
  email: '7J@C.DE',
  phone: '1-986-862-',
  department: 'Account Engineer',
  jobTitle: 'Product Applications Supervisor',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
