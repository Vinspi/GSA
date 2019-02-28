import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JsonResponse } from './request-interfaces/json-response';
import { environment } from '../../environments/environment';
import { Subject } from 'rxjs';
import {debounceTime} from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class AdminService {

  public messageAlert: string;
  public typeAlert: string;
  public _success = new Subject<string>();

  constructor(private http: HttpClient) { }

  public configureNotificationAlert(type: string, message: string, time: number) {
    this.typeAlert = type;
    this._success.subscribe((m) => this.messageAlert = m);
    this._success.pipe(
      debounceTime(time)
    ).subscribe(() => this.messageAlert = null);
    this._success.next(message);
  }

  private BASE_URL: String = environment.API_URL+environment.API_ADMIN;

  getWithdrawStats(data: any): Observable<JsonResponse> {
    return this.http.post<JsonResponse>(this.BASE_URL+'/stats', data, {withCredentials: true})
  }

  getAllSpeciesName(): Observable<JsonResponse> {

    return this.http.get<JsonResponse>(this.BASE_URL+'/allspeciesnames', {withCredentials: true})
  }

  addProduct(data: any): Observable<JsonResponse> {

    return this.http.post<JsonResponse>(this.BASE_URL+'/addproduct', data, {withCredentials: true})
  }
  getTriggeredAlerts(): Observable<JsonResponse> {
    return this.http.get<JsonResponse>(this.BASE_URL+"/triggeredAlerts")
  }

  getAllAlerts(): Observable<JsonResponse> {
    return this.http.get<JsonResponse>(this.BASE_URL+'/getAllAlerts', {withCredentials: true});
  }

  removeAlert(id: number): Observable<JsonResponse> {
    return this.http.post<JsonResponse>(this.BASE_URL+'/removeAlert', {alertId: id}, {withCredentials: true});
  }

  updateAlert(id: number, seuil: number): Observable<JsonResponse> {
    return this.http.post<JsonResponse>(this.BASE_URL+'/updateAlert', {alertId: id, seuil: seuil}, {withCredentials: true});
  }

  addAliquote(data: any): Observable<JsonResponse> {

    return this.http.post<JsonResponse>(this.BASE_URL+'/addAliquote', data, {withCredentials: true})
  }

  getAllProductsName(): Observable<JsonResponse> {

    return this.http.get<JsonResponse>(this.BASE_URL+'/allProducts', {withCredentials: true})
  }
  

}
