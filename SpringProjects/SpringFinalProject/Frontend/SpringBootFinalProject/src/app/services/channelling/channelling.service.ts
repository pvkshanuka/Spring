import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ChannellingService {

  _url = 'http://localhost:8060/channelling';

  constructor(private _http: HttpClient) { }

  save(doctorData) {
    return this._http.post<any>(this._url, doctorData);
  }

  loadAll() {
    return this._http.get<any>(this._url);
  }

  test() {
    return this._http.get<any>(this._url + '/test');
  }
}
