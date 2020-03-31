import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HospitalService {

  _url = 'http://localhost:8010/hospital';

  constructor(private _http: HttpClient) { }

  save(hospitalData) {
    return this._http.post<any>(this._url, hospitalData);
  }

  getAll() {
    return this._http.get<any>(this._url);
  }

}
