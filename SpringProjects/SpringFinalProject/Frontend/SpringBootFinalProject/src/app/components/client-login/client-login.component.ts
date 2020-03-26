import { ClientService } from './../../services/client/client.service';
import { OauthService } from './../../services/oauth/oauth.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators, FormBuilder } from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import { MatSnackBar } from '@angular/material/snack-bar';

// export class MyErrorStateMatcher implements ErrorStateMatcher {
//   isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
//     const isSubmitted = form && form.submitted;
//     return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
//   }
// }

@Component({
  selector: 'app-client-login',
  templateUrl: './client-login.component.html',
  styleUrls: ['./client-login.component.css']
})
export class ClientLoginComponent implements OnInit {

  inProcess = false;

  loginForm = this.fb.group({
    // client_secret: ['', []],
    // client_id: ['', []],
    // grant_type: ['', [Validators.required]],
    username: ['', [Validators.required,Validators.email]],
    password: ['', [Validators.required]],
  }
  );

  constructor(
    private fb: FormBuilder,
    private _snackBar: MatSnackBar,
    private _clientLogin: ClientService) { }



  ngOnInit(): void {
  }


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
              // if (response.success) {
              //   this._snackBar.open(response.message, '', {
              //     duration: 3000,
              //     panelClass: ['snackbar-success']
              //   });
              // } else {
              //   this._snackBar.open(response.message, '', {
              //     duration: 3000,
              //     panelClass: ['snackbar-error']
              //   });
              // }
              this.inProcess = false;
            },
            error => {
              console.log(error);
              this.inProcess = false;
            }
          );
    }
  }

}
