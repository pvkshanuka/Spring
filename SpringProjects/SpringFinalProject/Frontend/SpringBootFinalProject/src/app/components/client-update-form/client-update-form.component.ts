import { UserDetails, DataService } from './../../services/data/data.service';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ClientService } from './../../services/client/client.service';
import { MustMatch } from 'src/support/mustmatch';
import { Validators, FormBuilder } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-client-update-form',
  templateUrl: './client-update-form.component.html',
  styleUrls: ['./client-update-form.component.css']
})
export class ClientUpdateFormComponent implements OnInit {

  userDetails: UserDetails;

  userData;

  nametxt: string;
  agetxt: number;
  contacttxt: string;

  clientForm = this.fb.group({
    id: [],
    name: ['', [Validators.required, Validators.minLength(4)]],
    age: ['', [Validators.required, Validators.min(16), Validators.max(100)]],
    contact: ['', [Validators.required, Validators.pattern('^[0-9]{9}$')]]
  }
  );

  clientFormPW = this.fb.group({
    id: [],
    password: ['', [Validators.required, Validators.pattern('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&].{6,15}$')]],
    cpword: ['', [Validators.required]]
  }, {
    validator: MustMatch('password', 'cpword')
  }
  );

  inProcess = false;

  constructor(
    private data: DataService,
    private fb: FormBuilder,
    private _clientService: ClientService,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.data.userDetails.subscribe((user) => (this.userDetails = user));
    console.log(this.userDetails);

    this.loadUserData();

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

  get password() {
    return this.clientFormPW.get('password');
  }

  get cpword() {
    return this.clientFormPW.get('cpword');
  }

loadUserData() {
  this._clientService.loadUserData(this.userDetails.id).subscribe(
        response => {
          console.log(response);
          if (response == null) {
            this._snackBar.open('Invalid user to load data!', '', {
              duration: 3000,
              panelClass: ['snackbar-error']
            });
          } else {
            this.userData = response;
            this.nametxt = this.userData.name;
            this.agetxt = this.userData.age;
            this.contacttxt = this.userData.contact;

          }

          this.inProcess = false;
        },
        error => {
          console.log(error);
          this.inProcess = false;
          this._snackBar.open('User data load error!', '', {
            duration: 3000,
            panelClass: ['snackbar-error']
          });
        }
      );
}

  update() {
    this.clientForm.value.id = this.userDetails.id;
    console.log(this.clientForm.value);
    this.inProcess = true;
    if (this.clientForm.valid) {
      console.log(this.clientForm.value);
      this._clientService.update(this.clientForm.value).subscribe(
        response => {
          // console.log(response);
          if (response.success) {
            this._snackBar.open(response.message, '', {
              duration: 3000,
              panelClass: ['snackbar-success']
            });

            console.log(this.userDetails);


            this.userDetails.name = this.clientForm.value.name;

            sessionStorage.setItem('name', this.clientForm.value.name);

            this.data.changeUserDetails(this.userDetails);

            console.log(this.userDetails);

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
          this._snackBar.open('Update error!', '', {
            duration: 3000,
            panelClass: ['snackbar-error']
          });
        }
      );
    }
  }

  updatePW() {
    this.clientFormPW.value.id = this.userDetails.id;
    this.inProcess = true;
    if (this.clientFormPW.valid) {
      console.log(this.clientFormPW.value);
      this._clientService.updatePw(this.clientFormPW.value).subscribe(
        response => {
          console.log(response);
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
          this._snackBar.open('Update error!', '', {
            duration: 3000,
            panelClass: ['snackbar-error']
          });
        }
      );
    }
  }



}
