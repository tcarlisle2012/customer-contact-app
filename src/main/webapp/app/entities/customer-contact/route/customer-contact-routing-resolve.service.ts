import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICustomerContact } from '../customer-contact.model';
import { CustomerContactService } from '../service/customer-contact.service';

@Injectable({ providedIn: 'root' })
export class CustomerContactRoutingResolveService implements Resolve<ICustomerContact | null> {
  constructor(protected service: CustomerContactService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomerContact | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((customerContact: HttpResponse<ICustomerContact>) => {
          if (customerContact.body) {
            return of(customerContact.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
