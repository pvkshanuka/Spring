import { MatDialog } from '@angular/material/dialog';
import { ClientRegComponent } from './../client-reg/client-reg.component';
import { UserDetails, DataService } from './../../services/data/data.service';
import { AppComponent } from './../../app.component';
import { ClientService } from './../../services/client/client.service';
import { OauthService } from './../../services/oauth/oauth.service';
import { Component, OnInit } from '@angular/core';
import {
  FormControl,
  FormGroupDirective,
  NgForm,
  Validators,
  FormBuilder
} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Route } from '@angular/compiler/src/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-client-login',
  templateUrl: './client-login.component.html',
  styleUrls: ['./client-login.component.css']
})
export class ClientLoginComponent implements OnInit {
  inProcess = false;

  userDetails: UserDetails;

  loginForm = this.fb.group({
    username: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]]
  });

  constructor(
    private router: Router,
    private fb: FormBuilder,
    public dialog: MatDialog,
    private _snackBar: MatSnackBar,
    private _clientLogin: ClientService,
    private data: DataService
  ) {}

  ngOnInit(): void {}

  get username() {
    return this.loginForm.get('username');
  }

  get password() {
    return this.loginForm.get('password');
  }

  login() {
    console.log('awaaa');
    this.inProcess = true;
    if (this.loginForm.valid) {
      console.log(this.loginForm.value);
      this._clientLogin.logIn(this.loginForm.value).subscribe(
        response => {
          console.log(response);
          if (response.isSuccess) {

            sessionStorage.setItem('id', response.id);
            sessionStorage.setItem('hospital', response.hospital);
            sessionStorage.setItem('token', response.token);
            sessionStorage.setItem('username', response.username);
            sessionStorage.setItem('name', response.name);
            sessionStorage.setItem('type', response.type);

            this.userDetails = new UserDetails(response.id, response.name, response.hospital, response.username, response.token, response.type);

            this.data.changeUserDetails(this.userDetails);

            this._snackBar.open('Successfully Logedin.!', '', {
              duration: 3000,
              panelClass: ['snackbar-success']
            });

            console.log(this.userDetails);

            if (this.userDetails.type == 1) {
              this.router.navigate(['admin']);
            } else if (this.userDetails.type == 2) {
              this.router.navigate(['manager']);
            } else {
              this.router.navigate(['home']);
            }

          } else {
            console.log('Not success');

            this._snackBar.open(response.message, '', {
              duration: 3000,
              panelClass: ['snackbar-error']
            });
          }
          this.inProcess = false;
        },
        error => {
          console.log('ERROR');

          console.log(error);
          this.inProcess = false;
          this._snackBar.open('Login Error.!', '', {
            duration: 3000,
            panelClass: ['snackbar-error']
          });
        }
      );
    }
  }

  openSignUpDialog() {
    this.dialog.open(ClientRegComponent, {
      height: 'fit',
      width: 'fit',
    });
  }

}
