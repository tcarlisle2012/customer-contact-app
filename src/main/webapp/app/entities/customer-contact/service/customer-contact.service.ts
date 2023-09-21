import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICustomerContact, NewCustomerContact } from '../customer-contact.model';

export type PartialUpdateCustomerContact = Partial<ICustomerContact> & Pick<ICustomerContact, 'id'>;

export type EntityResponseType = HttpResponse<ICustomerContact>;
export type EntityArrayResponseType = HttpResponse<ICustomerContact[]>;

@Injectable({ providedIn: 'root' })
export class CustomerContactService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/customer-contacts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(customerContact: NewCustomerContact): Observable<EntityResponseType> {
    return this.http.post<ICustomerContact>(this.resourceUrl, customerContact, { observe: 'response' });
  }

  update(customerContact: ICustomerContact): Observable<EntityResponseType> {
    return this.http.put<ICustomerContact>(`${this.resourceUrl}/${this.getCustomerContactIdentifier(customerContact)}`, customerContact, {
      observe: 'response',
    });
  }

  partialUpdate(customerContact: PartialUpdateCustomerContact): Observable<EntityResponseType> {
    return this.http.patch<ICustomerContact>(`${this.resourceUrl}/${this.getCustomerContactIdentifier(customerContact)}`, customerContact, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICustomerContact>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICustomerContact[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCustomerContactIdentifier(customerContact: Pick<ICustomerContact, 'id'>): number {
    return customerContact.id;
  }

  compareCustomerContact(o1: Pick<ICustomerContact, 'id'> | null, o2: Pick<ICustomerContact, 'id'> | null): boolean {
    return o1 && o2 ? this.getCustomerContactIdentifier(o1) === this.getCustomerContactIdentifier(o2) : o1 === o2;
  }

  addCustomerContactToCollectionIfMissing<Type extends Pick<ICustomerContact, 'id'>>(
    customerContactCollection: Type[],
    ...customerContactsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const customerContacts: Type[] = customerContactsToCheck.filter(isPresent);
    if (customerContacts.length > 0) {
      const customerContactCollectionIdentifiers = customerContactCollection.map(
        customerContactItem => this.getCustomerContactIdentifier(customerContactItem)!
      );
      const customerContactsToAdd = customerContacts.filter(customerContactItem => {
        const customerContactIdentifier = this.getCustomerContactIdentifier(customerContactItem);
        if (customerContactCollectionIdentifiers.includes(customerContactIdentifier)) {
          return false;
        }
        customerContactCollectionIdentifiers.push(customerContactIdentifier);
        return true;
      });
      return [...customerContactsToAdd, ...customerContactCollection];
    }
    return customerContactCollection;
  }
}
