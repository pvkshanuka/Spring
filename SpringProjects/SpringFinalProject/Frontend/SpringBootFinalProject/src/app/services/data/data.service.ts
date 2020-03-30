import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export class UserDetails {
  name: string = null;
  username: string;
  token: string;
  type: string;

  constructor(name: string, username: string, token: string, type: string){
    this.name = name;
    this.username = username;
    this.token = token;
    this.type = type;
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
