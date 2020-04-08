import { ClientService } from './../../services/client/client.service';
import { Component, OnInit, NgModule } from '@angular/core';
import {
  FormControl,
  FormGroupDirective,
  NgForm,
  Validators,
  FormBuilder
} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MustMatch } from 'src/support/mustmatch';

// export class MyErrorStateMatcher implements ErrorStateMatcher {
//   isErrorState(
//     control: FormControl | null,
//     form: FormGroupDirective | NgForm | null
//   ): boolean {
//     const isSubmitted = form && form.submitted;
//     return !!(
//       control &&
//       control.invalid &&
//       (control.dirty || control.touched || isSubmitted)
//     );
//   }
// }

@Component({
  selector: 'app-client-reg',
  templateUrl: './client-reg.component.html',
  styleUrls: ['./client-reg.component.css']
})
export class ClientRegComponent implements OnInit {
  // emailFormControl = new FormControl('', [
  //   Validators.required,
  //   Validators.email
  // ]);

  // matcher = new MyErrorStateMatcher();


  clientForm = this.fb.group({
    name: ['', [Validators.required, Validators.minLength(4)]],
    age: ['', [Validators.required, Validators.min(16), Validators.max(100)]],
    contact: ['', [Validators.required, Validators.pattern('^[0-9]{9}$')]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&].{6,15}$')]],
    cpword: ['', [Validators.required]]
  }, {
    validator: MustMatch('password', 'cpword')
  }
  );

  inProcess = false;

  constructor(
    private fb: FormBuilder,
    private _clientService: ClientService,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {}

  get name() {
    return this.clientForm.get('name');
  }

  get age() {
    return this.clientForm.get('age');
  }

  get contact() {
    return this.clientForm.get('contact');
  }

  get email() {
    return this.clientForm.get('email');
  }

  get password() {
    return this.clientForm.get('password');
  }

  get cpword() {
    return this.clientForm.get('cpword');
  }


  save() {
    console.log('awaaa');
    this.inProcess = true;
    if (this.clientForm.valid) {
        console.log(this.clientForm.value);
        this._clientService.save(this.clientForm.value).subscribe(
          response => {
            // console.log(response);
            if (response.success) {
              this._snackBar.open(response.message, '', {
                duration: 3000,
                panelClass: ['snackbar-success']
              });
            } else {
              this._snackBar.open(response.message, '', {
                duration: 3000,
                panelClass: ['snackbar-error']
              });
            }
            this.inProcess = false;
          },
          error => {
            console.log(error);
            this.inProcess = false;
            this._snackBar.open('Sign Up Error!', '', {
              duration: 3000,
              panelClass: ['snackbar-error']
            });
          }
        );
      }
  }


}
