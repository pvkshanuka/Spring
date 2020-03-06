import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {RegisterPayload} from '../components/auth/register-payload';
import {Observable} from 'rxjs';
import {LoginPayload} from '../components/auth/login-payload';
import {JwtAutResponse} from '../components/auth/jwt-aut-response';
import {map} from 'rxjs/operators';
import {LocalStorageService} from 'ngx-webstorage';
import {isBoolean} from 'util';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  bool: boolean;

  constructor(private httpClient: HttpClient, private localStorageService: LocalStorageService) {
  }

  private url = 'http://localhost:8181/api/auth/';
  as;
  any;

  // login;

  register(registerPayload: RegisterPayload): Observable<any> {
    return this.httpClient.post(this.url + 'signup', registerPayload);
  }

  // login(loginPayload: LoginPayload): boolean {
  login(loginPayload: LoginPayload): Observable<boolean> {

    return this.httpClient.post<JwtAutResponse>(this.url + 'login', loginPayload).pipe(map(data => {
      this.localStorageService.store('authenticationToke', data.authenticationToken);
      this.localStorageService.store('username', data.username);
      return true;

    }));


    // this.httpClient.post<JwtAutResponse>(this.url + 'login', loginPayload).subscribe(data => {
    //   this.localStorageService.store('authenticationToke', data.authenticationToken);
    //   this.localStorageService.store('username', data.username);
    //   this.bool = true;
    // }, error => {
    //   this.bool = false;
    // });
    // return this.bool;

  }

  isAuthenticated(): boolean {
    return  this.localStorageService.retrieve('username') != null;
  }

}
