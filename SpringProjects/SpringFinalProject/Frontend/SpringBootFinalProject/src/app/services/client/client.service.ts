import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  _url = 'http://localhost:8040/client';

  constructor(private _http: HttpClient) { }

  save(doctorData) {
    return this._http.post<any>(this._url, doctorData);
  }

}
