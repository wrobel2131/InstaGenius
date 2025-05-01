import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {UpdateUser, User} from "../models/user.model";

@Injectable({
  providedIn: 'root'
})
export class UserApiService {
  private readonly httpClient: HttpClient = inject(HttpClient);
  private readonly USER_API_PATH = "/api/v1/users";


  getUserProfile() : Observable<User> {
    return this.httpClient.get<User>(`${this.USER_API_PATH}/profile`);
  }

  updateUserProfile(user: UpdateUser): Observable<User> {
    return this.httpClient.put<User>(`${this.USER_API_PATH}/profile`, user);
  }

}
