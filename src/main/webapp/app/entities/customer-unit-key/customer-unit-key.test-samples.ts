import { ICustomerUnitKey, NewCustomerUnitKey } from './customer-unit-key.model';

export const sampleWithRequiredData: ICustomerUnitKey = {
  id: 34090,
  customerNumber: 'synergies contin',
  salesOrganization: 'PCI generate Pat',
  distributionChannel: 'Borders Maryland',
  division: 'XML invoice fire',
};

export const sampleWithPartialData: ICustomerUnitKey = {
  id: 62962,
  customerNumber: 'Court dot-com Pr',
  salesOrganization: 'Quality violet',
  distributionChannel: 'calculate',
  division: 'Plaza Cliff Indu',
};

export const sampleWithFullData: ICustomerUnitKey = {
  id: 47260,
  customerNumber: 'SDD Awesome Cred',
  salesOrganization: 'FTP',
  distributionChannel: 'Tasty',
  division: 'payment',
};

export const sampleWithNewData: NewCustomerUnitKey = {
  customerNumber: 'monetize',
  salesOrganization: 'program program ',
  distributionChannel: 'Research Account',
  division: 'Shore',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
