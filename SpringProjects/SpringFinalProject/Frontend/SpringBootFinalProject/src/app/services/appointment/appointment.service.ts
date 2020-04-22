import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {


  _url = 'http://169.254.31.42:8050/appointment';

  constructor(private _http: HttpClient) { }

  save(appoinmentData) {
    return this._http.post<any>(this._url, appoinmentData);
  }

  loadAllByUser(appointmentSearchDTO) {
    return this._http.post<any>(this._url + '/getAppointments', appointmentSearchDTO);
  }

  delete(id) {
    return this._http.delete<any>(this._url + '/' + id);
  }

  visited(id) {
    return this._http.get<any>(this._url + '/visit/' + id);
  }

}
