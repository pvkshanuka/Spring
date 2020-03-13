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

  constructor(public dialog: MatDialog) {}

  ngOnInit(): void {
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
