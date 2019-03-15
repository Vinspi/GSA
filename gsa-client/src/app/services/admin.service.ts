import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JsonResponse } from './request-interfaces/json-response';
import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

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
    return this.http.get<JsonResponse>(this.BASE_URL+"/triggeredAlerts", {withCredentials: true});
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

    return this.http.post<JsonResponse>(this.BASE_URL+'/addAliquote', data, {withCredentials: true});
  }

  getAllProductsName(): Observable<JsonResponse> {

    return this.http.get<JsonResponse>(this.BASE_URL+'/allProducts', {withCredentials: true});
  }
  
  transfertAliquot(data: any): Observable<JsonResponse> {
    return this.http.post<JsonResponse>(this.BASE_URL+'/transfertAliquot', data, {withCredentials: true});
  }

  addAlert(data: any): Observable<JsonResponse> {
    return this.http.post<JsonResponse>(this.BASE_URL+'/addAlert', data, {withCredentials: true});
  }

  saveTeamReport(data: any): Observable<JsonResponse> {
    return this.http.post<JsonResponse>(this.BASE_URL + '/saveReport', data, { withCredentials: true });
  }

  getQuarterlyTransactionLosses(quarter: string, year: string): Observable<JsonResponse> {
    return this.http.get<JsonResponse>(this.BASE_URL + '/transactionLosses?quarter=' + quarter + '&year=' + year, { withCredentials: true });
  }

  getQuarterlyWithdrawnTransactionsByTeamNameAndYear(teamName: string, quarter: string, year: string): Observable<JsonResponse> {
    return this.http.get<JsonResponse>(this.BASE_URL + '/withdrawnTransactions?teamName=' + teamName + '&quarter=' + quarter + '&year=' + year, { withCredentials: true });
  }

  getReportLosses(quarter: string, year: string): Observable<JsonResponse> {
    return this.http.get<JsonResponse>(this.BASE_URL + '/teamReportLosses?quarter=' + quarter + '&year=' + year, { withCredentials: true });
  }

  getQuarterAndYearValuesOfEditableReports() {
    return this.http.get<JsonResponse>(this.BASE_URL + '/quarterYearOfEditableReports', { withCredentials: true });
  }

  getSumOfQuarterLosses(quarter: string, year: string) {
    return this.http.get<JsonResponse>(this.BASE_URL + '/sumQuarterLosses?quarter=' + quarter + '&year=' + year, { withCredentials: true });
  }

  getAllProductsWithAliquots(): Observable<JsonResponse> {
    return this.http.get<JsonResponse>(this.BASE_URL+'/getAllProductsWithAliquots', {withCredentials: true});
  }

  postInventoryForm(form: any): Observable<JsonResponse> {
    return this.http.post<JsonResponse>(this.BASE_URL+'/handleInventory',form, {withCredentials: true});
  }

  getProvidersStats(): Observable<JsonResponse> {
    return this.http.get<JsonResponse>(this.BASE_URL+'/getProvidersStats', {withCredentials: true});
  }

  getAlertNotification(): Observable<JsonResponse> {
    return this.http.get<JsonResponse>(this.BASE_URL+'/getAlertsNotification', {withCredentials: true});
  }

  getReportNotification(): Observable<JsonResponse> {
    return this.http.get<JsonResponse>(this.BASE_URL+'/getNextReport', {withCredentials: true});
  }

  getProductsStats(): Observable<JsonResponse> {
    return this.http.get<JsonResponse>(this.BASE_URL+'/getProductsStats', {withCredentials: true});
  }

  setMaintenanceMode(data: any): Observable<JsonResponse> {
    return this.http.post<JsonResponse>(this.BASE_URL+'/setupMaintenanceMode', data, {withCredentials: true});
  }
}
