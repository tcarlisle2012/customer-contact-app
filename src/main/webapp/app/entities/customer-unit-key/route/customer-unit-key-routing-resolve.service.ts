import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICustomerUnitKey } from '../customer-unit-key.model';
import { CustomerUnitKeyService } from '../service/customer-unit-key.service';

@Injectable({ providedIn: 'root' })
export class CustomerUnitKeyRoutingResolveService implements Resolve<ICustomerUnitKey | null> {
  constructor(protected service: CustomerUnitKeyService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomerUnitKey | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((customerUnitKey: HttpResponse<ICustomerUnitKey>) => {
          if (customerUnitKey.body) {
            return of(customerUnitKey.body);
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
