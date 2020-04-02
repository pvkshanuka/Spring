import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {


  _url = 'http://localhost:8050/appointment';

  constructor(private _http: HttpClient) { }

  save(appoinmentData) {
    return this._http.post<any>(this._url, appoinmentData);
  }

}
