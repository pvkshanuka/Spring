import { HospitalService } from '../../services/hospital/hospital.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

// export class MyErrorStateMatcher implements ErrorStateMatcher {
//   isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
//     const isSubmitted = form && form.submitted;
//     return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
//   }
// }

@Component({
  selector: 'app-hospital-form',
  templateUrl: './hospital-form.component.html',
  styleUrls: ['./hospital-form.component.css']
})
export class HospitalFormComponent implements OnInit {
  // emailFormControl = new FormControl('', [
  //   Validators.required,
  //   Validators.email,
  // ]);

  // matcher = new MyErrorStateMatcher();
  hospitalForm = this.fb.group({
    name: ['', [Validators.required, Validators.minLength(4)]],
    city: ['', [Validators.required, Validators.minLength(4)]],
    contact: ['', [Validators.required, Validators.pattern('^[0-9]{9}$')]],
    email: ['', [Validators.required, Validators.email]]
  });

  inProcess = false;

  constructor(
    private fb: FormBuilder,
    private _hospitalService: HospitalService,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {}

  get name() {
    return this.hospitalForm.get('name');
  }

  get city() {
    return this.hospitalForm.get('city');
  }

  get contact() {
    return this.hospitalForm.get('contact');
  }

  get email() {
    return this.hospitalForm.get('email');
  }

  show() {}

  save() {
    this.inProcess = true;
    if (this.hospitalForm.valid) {
      console.log(this.hospitalForm.value);
      this._hospitalService.save(this.hospitalForm.value).subscribe(
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
        }
      );
    }
  }
}
