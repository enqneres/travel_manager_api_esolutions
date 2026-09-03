import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { AuthPayload, AuthResponse } from './auth.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly tokenKey = 'atlas.jwt';

  register(payload: AuthPayload): Observable<AuthResponse> {
    return this.http.post<AuthResponse>('/api/auth/register', payload).pipe(tap(response => this.store(response)));
  }

  login(payload: AuthPayload): Observable<AuthResponse> {
    return this.http.post<AuthResponse>('/api/auth/login', payload).pipe(tap(response => this.store(response)));
  }

  token(): string | null { return localStorage.getItem(this.tokenKey); }
  logout(): void { localStorage.removeItem(this.tokenKey); }
  private store(response: AuthResponse): void { localStorage.setItem(this.tokenKey, response.token); }
}
