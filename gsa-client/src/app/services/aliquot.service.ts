import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JsonResponse } from './request-interfaces/json-response';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AliquotService {

  constructor(private http: HttpClient) { }
  getAliquots(): Observable<JsonResponse> {
    return this.http.get<JsonResponse>(environment.API_URL+'/aliquot/get-aliquots',{withCredentials: true})
  }

  // save au lieu 
   removeAliquots(id: number): Observable<JsonResponse> {
     return this.http.post<JsonResponse>(environment.API_URL+'/aliquot/delete-aliquot/'+id,{withCredentials: true})
   }
}
