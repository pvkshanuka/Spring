import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ChannellingService {

  _url = 'http://169.254.31.42:8060/channelling';

  constructor(private _http: HttpClient) { }

  save(doctorData) {
    return this._http.post<any>(this._url, doctorData);
  }

  delete(id) {
    return this._http.delete<any>(this._url + '/' + id);
  }

  start(id) {
    return this._http.get<any>(this._url + '/startChannelling/' + id);
  }

  finish(id) {
    return this._http.get<any>(this._url + '/finishChannelling/' + id);
  }

  loadAll(channellingSearchDTO) {
    return this._http.post<any>(this._url + '/search', channellingSearchDTO);
  }

  loadAllByHospital(channellingSearchDTO) {
    return this._http.post<any>(this._url + '/searchByHospital', channellingSearchDTO);
  }


  test() {
    return this._http.get<any>(this._url + '/test');
  }
}
