import { Injectable } from '@angular/core';

import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JsonResponse } from './request-interfaces/json-response';
import { LocalStorage } from '@ngx-pwa/local-storage';

@Injectable({
  providedIn: 'root'
})

export class AuthentificationService {


  constructor(private http: HttpClient, private localStorage: LocalStorage) { }


  checkLogin(): Observable<JsonResponse> {
    const headers = new HttpHeaders().set("withCredentials", "true");
    

    return this.http.get<JsonResponse>(environment.API_URL+'/auth/checkLogin', {withCredentials: true});

  }

  login(email: String, password: String): Observable<JsonResponse> {

    const headers = new HttpHeaders().set("Access-Control-Allow-Credentials", "true");
    

    return this.http.post<JsonResponse>(environment.API_URL+'/auth/login', {
      email: email,
      password: password
    },{withCredentials: true});
  }

  logout(): Observable<JsonResponse> {
    return this.http.get<JsonResponse>(environment.API_URL+'/auth/logout',{withCredentials: true});
  }

  
}
