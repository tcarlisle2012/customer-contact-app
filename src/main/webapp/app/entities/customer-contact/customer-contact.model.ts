import { ICustomerUnitKey } from 'app/entities/customer-unit-key/customer-unit-key.model';

export interface ICustomerContact {
  id: number;
  firstName?: string | null;
  lastName?: string | null;
  displayName?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  customerUnitKey?: Pick<ICustomerUnitKey, 'id' | 'customerNumber'> | null;
}

export type NewCustomerContact = Omit<ICustomerContact, 'id'> & { id: null };
