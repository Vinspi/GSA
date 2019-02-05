import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

export interface Lorem {
  text: String;
}

@Injectable({
  providedIn: 'root'
})

export class LoremServiceService {

  loremUrl = "http://localhost:8080/lorem"

  fetchLorem(): Observable<Lorem> {
    return this.http.get<Lorem>(this.loremUrl)
  }

  constructor(private http: HttpClient) { }
}
