import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { Travel, TravelPage, TravelPayload } from './travel.model';

@Injectable({ providedIn: 'root' })
export class TravelService {
  private readonly http = inject(HttpClient);
  private readonly endpoint = '/api/travels';

  list(page = 0, size = 10): Observable<TravelPage> {
    const params = new HttpParams().set('page', page).set('size', Math.min(size, 50));
    return this.http.get<TravelPage>(this.endpoint, { params });
  }

  create(payload: TravelPayload): Observable<Travel> {
    return this.http.post<Travel>(this.endpoint, payload);
  }
}
