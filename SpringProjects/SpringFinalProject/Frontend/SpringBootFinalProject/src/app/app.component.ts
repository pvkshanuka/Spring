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

            data.changeUserDetails(new UserDetails(parseInt(sessionStorage.getItem('id')), sessionStorage.getItem('name'),
            parseInt(sessionStorage.getItem('id')),sessionStorage.getItem('username'), sessionStorage.getItem('token'), parseInt(sessionStorage.getItem('type'))));

          }
  }

  title = 'SpringBootFinalProject';
}
