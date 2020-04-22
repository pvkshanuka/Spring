import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HospitalService {

  _url = 'http://169.254.31.42:8010/hospital';

  constructor(private _http: HttpClient) { }

  save(hospitalData) {
    return this._http.post<any>(this._url, hospitalData);
  }

  getAll() {
    return this._http.get<any>(this._url);
  }

  findByNameStartsWith(name) {
    return this._http.get<any>(this._url + '/findByNameStartsWith/' + name);
  }

  delete(id) {
    return this._http.delete<any>(this._url + '/' + id);
  }

}
