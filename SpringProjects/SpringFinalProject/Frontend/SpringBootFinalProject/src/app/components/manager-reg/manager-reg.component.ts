import { DataService, UserDetails } from './../../services/data/data.service';
import { ClientService } from './../../services/client/client.service';
import { Component, OnInit, NgModule, Inject } from '@angular/core';
import {
  FormControl,
  FormGroupDirective,
  NgForm,
  Validators,
  FormBuilder,
} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MustMatch } from 'src/support/mustmatch';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-manager-reg',
  templateUrl: './manager-reg.component.html',
  styleUrls: ['./manager-reg.component.css'],
})
export class ManagerRegComponent implements OnInit {
  clientForm = this.fb.group({
    name: ['', [Validators.required, Validators.minLength(4)]],
    age: ['', [Validators.required, Validators.min(16), Validators.max(100)]],
    contact: ['', [Validators.required, Validators.pattern('^[0-9]{9}$')]],
    email: ['', [Validators.required, Validators.email]],
    type: [1],
    hospital: [''],
  });

  userDetails: UserDetails;

  inProcess = false;

  hospital;

  constructor(
    @Inject(MAT_DIALOG_DATA) dialogData,
    private data: DataService,
    private fb: FormBuilder,
    private _clientService: ClientService,
    private _snackBar: MatSnackBar
  ) {
    if (dialogData != null) {
      this.hospital = dialogData.hospital;
    }
  }

  ngOnInit(): void {
    this.data.userDetails.subscribe((user) => (this.userDetails = user));
  }

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
    if (this.hospital && this.hospital != 'admin') {
      console.log('awaaa ' + this.hospital);
      this.saveManagerByAdmin(this.hospital);
    } else if (this.hospital === 'admin') {
        console.log('awaaa save' + this.hospital);
        this.saveAdmin();
      }else{

      console.log('awaaa else');

      this.inProcess = true;
      if (this.clientForm.valid) {
        console.log(this.clientForm.value);

        this._clientService.saveManager(this.clientForm.value).subscribe(
          (response) => {
            // console.log(response);
            if (response.success) {
              this._snackBar.open(response.message, '', {
                duration: 3000,
                panelClass: ['snackbar-success'],
              });
            } else {
              this._snackBar.open(response.message, '', {
                duration: 3000,
                panelClass: ['snackbar-error'],
              });
            }
            this.inProcess = false;
          },
          (error) => {
            console.log(error);
            this.inProcess = false;
            this._snackBar.open('Sign Up Error!', '', {
              duration: 3000,
              panelClass: ['snackbar-error'],
            });
          }
        );
      }
    }
  }

  saveAdmin(){
    console.log('saveAdmin');
    this.inProcess = true;
    if (this.clientForm.valid) {

      console.log(this.clientForm.value);
      this._clientService.saveAdmin(this.clientForm.value).subscribe(
        (response) => {
          // console.log(response);
          if (response.success) {
            this._snackBar.open(response.message, '', {
              duration: 3000,
              panelClass: ['snackbar-success'],
            });
          } else {
            this._snackBar.open(response.message, '', {
              duration: 3000,
              panelClass: ['snackbar-error'],
            });
          }
          this.inProcess = false;
        },
        (error) => {
          console.log(error);
          this.inProcess = false;
          this._snackBar.open('Sign Up Error!', '', {
            duration: 3000,
            panelClass: ['snackbar-error'],
          });
        }
      );
    }
  }

  saveManagerByAdmin(hospital) {
    this.inProcess = true;
    if (this.clientForm.valid) {
      this.clientForm.value.hospital = hospital;

      console.log(this.clientForm.value);
      this._clientService.saveManagerByAdmin(this.clientForm.value).subscribe(
        (response) => {
          // console.log(response);
          if (response.success) {
            this._snackBar.open(response.message, '', {
              duration: 3000,
              panelClass: ['snackbar-success'],
            });
          } else {
            this._snackBar.open(response.message, '', {
              duration: 3000,
              panelClass: ['snackbar-error'],
            });
          }
          this.inProcess = false;
        },
        (error) => {
          console.log(error);
          this.inProcess = false;
          this._snackBar.open('Sign Up Error!', '', {
            duration: 3000,
            panelClass: ['snackbar-error'],
          });
        }
      );
    }
  }

  sendSampleEmail() {
    console.log(this.clientForm.value.email);
    this._clientService.sendSampleEmail(this.clientForm.value.email).subscribe(
      (response) => {
        console.log(response);
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
