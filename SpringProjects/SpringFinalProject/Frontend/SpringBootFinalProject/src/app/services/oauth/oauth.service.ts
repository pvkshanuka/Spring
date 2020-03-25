import { map } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class OauthService {

  constructor(
    private httpClient: HttpClient
  ) {}

  logIn(username, password) {
    return this.httpClient.post<any>('http://localhost:9191/oauth', { username, password }).pipe(
      map(
        data => {
          sessionStorage.setItem('username', username);
          const token = 'Bearer ' + data.token;
          sessionStorage.setItem('token', data);
          return data;
        }
      )

    );
  }


  isUserLoggedIn() {
    const user = sessionStorage.getItem('username');
    // console.log(!(user === null))
    return !(user === null);
  }

  logOut() {
    sessionStorage.removeItem('username')
  }

}
