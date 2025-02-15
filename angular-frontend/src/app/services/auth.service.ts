import { HttpClient } from '@angular/common/http';
import { Injectable, inject, signal } from '@angular/core';
import { Observable, map } from 'rxjs';
import {
  UserLoginCredentials,
  UserRegisterCredentials,
} from '../models/user.model';
import { AuthenticationResponse } from '../models/authentication-response.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http: HttpClient = inject(HttpClient);

  login(credentials: UserLoginCredentials): Observable<AuthenticationResponse> {
    console.log('login endpoint');
    return this.http.post<AuthenticationResponse>('api/login/', credentials);
  }

  register(credentials: UserRegisterCredentials): Observable<void> {
    console.log('register endpoint');
    return this.http.post<void>('api/register/', credentials);
  }

  setLocalStorage({
    userId,
    accessToken,
    refreshToken,
  }: AuthenticationResponse): void {
    localStorage.setItem('accessToken', accessToken);
    localStorage.setItem('refreshToken', refreshToken);
    localStorage.setItem('userId', String(userId));
  }

  clearLocalStorage(): void {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('userId');
  }
}
