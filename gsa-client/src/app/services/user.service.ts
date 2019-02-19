import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JsonResponse } from './request-interfaces/json-response';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getAllProductOverview(): Observable<JsonResponse> {
    return this.http.get<JsonResponse>(environment.API_URL+'/stockOverview');
  }

  getProductName(n: number): Observable<JsonResponse> {
    return this.http.post<JsonResponse>(environment.API_URL+'/getProductName', {nlot: n}, {withCredentials: true});
  }

  withdrawCart(data: any): Observable<JsonResponse> {
    return this.http.post<JsonResponse>(environment.API_URL+'/withdrawCart', data, {withCredentials: true});
  }

  getAllTeamName(): Observable<JsonResponse> {
    return this.http.get<JsonResponse>(environment.API_URL+'/getAllTeamName');
  }

  getAllProductName(): Observable<JsonResponse> {
    return this.http.get<JsonResponse>(environment.API_URL+'/getAllProductName');
  }

  getAllSpeciesName(): Observable<JsonResponse> {
    return this.http.get<JsonResponse>(environment.API_URL+'/admin/allSpeciesName');
  }

}
