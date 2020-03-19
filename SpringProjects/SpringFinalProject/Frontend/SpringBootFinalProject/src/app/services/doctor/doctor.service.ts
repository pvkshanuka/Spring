import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {

  _url = 'http://localhost:8020/doctor';

  constructor(private _http: HttpClient) { }

  save(doctorData) {
    return this._http.post<any>(this._url, doctorData);
  }

  get(category) {
    return this._http.get<any>(this._url, category);
  }

  getAll() {
    return this._http.get<any>(this._url);
  }

  getCats(doctor_id) {
    return this._http.get<any>(this._url + '/getCats/' + doctor_id);
  }

  test() {
    return this._http.get<any>(this._url + '/test');
  }

}
