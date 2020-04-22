import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {

  _url = 'http://169.254.31.42:8020/doctor';

  constructor(private _http: HttpClient) { }

  save(doctorData) {
    return this._http.post<any>(this._url, doctorData);
  }

  update(doctorData) {
    console.log('In Update');
    console.log(doctorData);
    return this._http.put<any>(this._url, doctorData);
  }

  delete(id) {
    return this._http.delete<any>(this._url + '/' + id);
  }

  get(category) {
    return this._http.get<any>(this._url, category);
  }

  getAll() {
    return this._http.get<any>(this._url);
  }

  findByNameStartsWith(name) {
    return this._http.get<any>(this._url + '/findByNameStartsWith/' + name);
  }

  getCats(doctor_id) {
    return this._http.get<any>(this._url + '/getCats/' + doctor_id);
  }

  test() {
    return this._http.get<any>(this._url + '/test');
  }

}
