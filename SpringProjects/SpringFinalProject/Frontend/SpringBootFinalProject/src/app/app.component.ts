import { DataService, UserDetails } from './services/data/data.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(data: DataService) {
    if(sessionStorage.getItem('token') != null) {

            data.changeUserDetails(new UserDetails(sessionStorage.getItem('name'),
             sessionStorage.getItem('username'), sessionStorage.getItem('token'), sessionStorage.getItem('type')));

          }
  }

  title = 'SpringBootFinalProject';
}
