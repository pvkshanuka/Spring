import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export class UserDetails {
  name: string = null;
  username: string;
  token: string;

  constructor(name: string, username: string, token: string){
    this.name = name;
    this.username = username;
    this.token = token;
  }

}

@Injectable({
  providedIn: 'root'
})
export class DataService {
  private userDetailsSource = new BehaviorSubject<UserDetails>(null);

  userDetails = this.userDetailsSource.asObservable();

  constructor() {}

  changeUserDetails(userDetails: UserDetails) {
    this.userDetailsSource.next(userDetails);
  }
}
