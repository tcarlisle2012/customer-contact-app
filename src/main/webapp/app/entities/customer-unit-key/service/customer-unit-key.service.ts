import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICustomerUnitKey, NewCustomerUnitKey } from '../customer-unit-key.model';

export type PartialUpdateCustomerUnitKey = Partial<ICustomerUnitKey> & Pick<ICustomerUnitKey, 'id'>;

export type EntityResponseType = HttpResponse<ICustomerUnitKey>;
export type EntityArrayResponseType = HttpResponse<ICustomerUnitKey[]>;

@Injectable({ providedIn: 'root' })
export class CustomerUnitKeyService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/customer-unit-keys');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(customerUnitKey: NewCustomerUnitKey): Observable<EntityResponseType> {
    return this.http.post<ICustomerUnitKey>(this.resourceUrl, customerUnitKey, { observe: 'response' });
  }

  update(customerUnitKey: ICustomerUnitKey): Observable<EntityResponseType> {
    return this.http.put<ICustomerUnitKey>(`${this.resourceUrl}/${this.getCustomerUnitKeyIdentifier(customerUnitKey)}`, customerUnitKey, {
      observe: 'response',
    });
  }

  partialUpdate(customerUnitKey: PartialUpdateCustomerUnitKey): Observable<EntityResponseType> {
    return this.http.patch<ICustomerUnitKey>(`${this.resourceUrl}/${this.getCustomerUnitKeyIdentifier(customerUnitKey)}`, customerUnitKey, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICustomerUnitKey>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICustomerUnitKey[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCustomerUnitKeyIdentifier(customerUnitKey: Pick<ICustomerUnitKey, 'id'>): number {
    return customerUnitKey.id;
  }

  compareCustomerUnitKey(o1: Pick<ICustomerUnitKey, 'id'> | null, o2: Pick<ICustomerUnitKey, 'id'> | null): boolean {
    return o1 && o2 ? this.getCustomerUnitKeyIdentifier(o1) === this.getCustomerUnitKeyIdentifier(o2) : o1 === o2;
  }

  addCustomerUnitKeyToCollectionIfMissing<Type extends Pick<ICustomerUnitKey, 'id'>>(
    customerUnitKeyCollection: Type[],
    ...customerUnitKeysToCheck: (Type | null | undefined)[]
  ): Type[] {
    const customerUnitKeys: Type[] = customerUnitKeysToCheck.filter(isPresent);
    if (customerUnitKeys.length > 0) {
      const customerUnitKeyCollectionIdentifiers = customerUnitKeyCollection.map(
        customerUnitKeyItem => this.getCustomerUnitKeyIdentifier(customerUnitKeyItem)!
      );
      const customerUnitKeysToAdd = customerUnitKeys.filter(customerUnitKeyItem => {
        const customerUnitKeyIdentifier = this.getCustomerUnitKeyIdentifier(customerUnitKeyItem);
        if (customerUnitKeyCollectionIdentifiers.includes(customerUnitKeyIdentifier)) {
          return false;
        }
        customerUnitKeyCollectionIdentifiers.push(customerUnitKeyIdentifier);
        return true;
      });
      return [...customerUnitKeysToAdd, ...customerUnitKeyCollection];
    }
    return customerUnitKeyCollection;
  }
}
