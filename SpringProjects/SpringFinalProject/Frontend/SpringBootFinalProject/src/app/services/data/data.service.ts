import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export class UserDetails {

  id: number = null;
  name: string = null;
  hospital: number = null;
  username: string = null;
  token: string = null;
  type: number = null;

  constructor(id: number, name: string, hospital: number, username: string, token: string, type: number){
    this.id = id;
    this.name = name;
    this.hospital = hospital;
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
