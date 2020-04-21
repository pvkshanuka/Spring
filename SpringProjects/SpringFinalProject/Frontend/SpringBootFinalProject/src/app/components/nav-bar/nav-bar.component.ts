import { DataService, UserDetails } from './../../services/data/data.service';
import { ClientLoginComponent } from './../client-login/client-login.component';
import { ClientRegComponent } from './../client-reg/client-reg.component';
import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  userDetails: UserDetails;

  constructor(public dialog: MatDialog, private data: DataService) {
  }

  ngOnInit(): void {
    this.data.userDetails.subscribe(user => this.userDetails = user);
  }

  logOut(){

    sessionStorage.removeItem('id');
    sessionStorage.removeItem('hospital');
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('username');
    sessionStorage.removeItem('name');
    sessionStorage.removeItem('type');

    this.data.changeUserDetails(null);
    window.location.reload();
  }

  openSignUpDialog() {
    this.dialog.open(ClientRegComponent, {
      height: 'fit',
      width: 'fit',
    });
  }

  openLoginDialog() {
    this.dialog.open(ClientLoginComponent, {
      height: 'fit',
      width: 'fit',
    });
  }

}
