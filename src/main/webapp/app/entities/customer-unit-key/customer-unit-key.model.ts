export interface ICustomerUnitKey {
  id: number;
  customerNumber?: string | null;
  salesOrganization?: string | null;
  distributionChannel?: string | null;
  division?: string | null;
}

export type NewCustomerUnitKey = Omit<ICustomerUnitKey, 'id'> & { id: null };
