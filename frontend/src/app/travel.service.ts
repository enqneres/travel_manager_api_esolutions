import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Travel, TravelPayload } from './travel.model';

@Injectable({ providedIn: 'root' })
export class TravelService {
  private readonly http = inject(HttpClient);
  private readonly endpoint = '/api/travels';

  list(): Observable<Travel[]> {
    return this.http.get<Travel[]>(this.endpoint);
  }

  create(payload: TravelPayload): Observable<Travel> {
    return this.http.post<Travel>(this.endpoint, payload);
  }
}
