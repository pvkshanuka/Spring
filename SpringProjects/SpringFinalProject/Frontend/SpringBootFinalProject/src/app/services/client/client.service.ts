import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  _url = 'http://localhost:8040/client';

  constructor(private _http: HttpClient) { }

  save(clientData: any) {
    return this._http.post<any>(this._url, clientData);
  }

  delete(id) {
    return this._http.delete<any>(this._url + '/' + id);
  }

  saveManager(clientData: any) {
    return this._http.post<any>(this._url + '/saveManager', clientData);
  }

  saveManagerByAdmin(clientData: any) {
    return this._http.post<any>(this._url + '/saveManagerByAdmin', clientData);
  }

  sendSampleEmail(email) {
    return this._http.post<any>(this._url + '/testMail', email);
  }

  loadUserData(id) {
    return this._http.get<any>(this._url + '/findDetailsById/' + id);
  }

  update(clientData: any) {
    return this._http.put<any>(this._url, clientData);
  }

  updatePw(password) {
    return this._http.put<any>(this._url + '/password', password);
  }

  resetPassword(id) {
    return this._http.get<any>(this._url + '/resetPassword/' + id);
  }

  logIn(loginData: any) {
    // console.log('OAuthService' + this._url);
    console.log(loginData);



  //   loginData.client_id = 'mobile';
  //   loginData.client_secret = 'pin';

  //   console.log(loginData);

  //   const httpOptions = {
  //     headers: new HttpHeaders({
  //         'Content-Type': 'application/x-www-form-urlencoded',
  //         Authorization: 'Basic ' + btoa('mobile' + ':' + 'pin')
  //     })
  // };

  //   const body = 'client_id=mobile&client_secret=pin&grant_type=password&username={2}&password={3}'
  //       .replace('{2}', loginData.username)
  //       .replace('{3}', loginData.password);

  //   console.log(body);

    // return this._http.post<any>(this._url, body, httpOptions);
    return this._http.post<any>(this._url + '/login', loginData);

    // return this.httpClient.post<any>('http://localhost:9191/oauth', loginData).pipe(
    //   map(
    //     data => {
    //       sessionStorage.setItem('username', username);
    //       const token = 'Bearer ' + data.token;
    //       sessionStorage.setItem('token', data);
    //       return data;
    //     }
    //   )

    // );
  }

}
