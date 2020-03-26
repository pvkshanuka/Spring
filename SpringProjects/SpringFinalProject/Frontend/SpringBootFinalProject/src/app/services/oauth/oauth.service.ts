import { map } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { url } from 'inspector';

@Injectable({
  providedIn: 'root'
})
export class OauthService {

  _url = 'http://localhost:9191/oauth/token';

  constructor(
    private _http: HttpClient
  ) {}


  // isUserLoggedIn() {
  //   const user = sessionStorage.getItem('username');
  //   // console.log(!(user === null))
  //   return !(user === null);
  // }

  // logOut() {
  //   sessionStorage.removeItem('username');
  // }

}
