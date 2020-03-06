import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {LoginPayload} from '../login-payload';
import {AuthService} from '../../../services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loginPayload: LoginPayload;

  constructor(private authService: AuthService, private router: Router) {
    this.loginForm = new FormGroup({
      username: new FormControl(),
      password: new FormControl()
    });
    this.loginPayload = {
      username: '',
      password: ''
    };
  }


  ngOnInit(): void {
  }

  onSubmit() {
    this.loginPayload.username = this.loginForm.get('username').value;
    this.loginPayload.password = this.loginForm.get('password').value;
    console.log(this.loginPayload.username);
    console.log(this.loginPayload.password);

    // if (this.authService.login(this.loginPayload)) {
    //   console.log('Login Success');
    // } else {
    //   console.log('Login Failed');
    // }

    this.authService.login(this.loginPayload).subscribe(data => {
      if (data) {
        console.log('Login Sucess');
        this.router.navigateByUrl('/home');
      } else {
        console.log('Login Failed');
      }
    }, error => {
      console.log('Login Failed');
    });

  }
}
