import { ChannellingService } from './../../services/channelling/channelling.service';
import { Category } from './../../modles/category-model';
import { DoctorService } from './../../services/doctor/doctor.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormBuilder, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { MatSnackBar } from '@angular/material/snack-bar';


// export interface User {
//   name: string;
// }

@Component({
  selector: 'app-channelling-form',
  templateUrl: './channelling-form.component.html',
  styleUrls: ['./channelling-form.component.css']
})
export class ChannellingFormComponent implements OnInit {
  minDate = new Date();
  selectedDate: Date;

  dateMills: number;

  // myControl = new FormControl();
  // options: User[] = [{ name: 'Mary' }, { name: 'Shelley' }, { name: 'Igor' }];
  // filteredOptions: Observable<User[]>;

  filteredOptions: Observable<any>;

  doctors;

  categoryies;

  channellingForm = this.fb.group({
    hospital: [''],
    doctor: ['', [Validators.required]],
    startTime: ['', [Validators.required]],
    endTime: ['', [Validators.required]],
    room: ['', [Validators.required]],
    price: ['', [Validators.required]]
  });

  inProcessSave = false;

  constructor(
    private fb: FormBuilder,
    private _doctorService: DoctorService,
    private _channellingService: ChannellingService,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    // this.filteredOptions = this.myControl.valueChanges.pipe(
    //   startWith(''),
    //   map(value => (typeof value === 'string' ? value : value.name)),
    //   map(name => (name ? this._filter(name) : this.options.slice()))
    // );

    // this.filteredOptions = this.channellingForm.get('doctor').valueChanges.pipe(
    //   startWith(''),
    //   map(value => (typeof value === 'string' ? value : value.name)),
    //   map(name => (name ? this._filter(name) : this.doctors.slice()))
    // );

    this.loadDoctors();
  }

  // displayFn(user: User): string {
  //   return user && user.name ? user.name : '';
  // }

  // private _filter(name: string): User[] {
  //   const filterValue = name.toLowerCase();

  //   return this.options.filter(
  //     option => option.name.toLowerCase().indexOf(filterValue) === 0
  //   );
  // }

  // private _filter(name: string): User[] {
  //   const filterValue = name.toLowerCase();

  //   return this.doctors.filter(
  //     option => option.name.toLowerCase().indexOf(filterValue) === 0
  //   );
  // }

  onStartDateChange(event) {
    // console.log(event);
    console.log(this.selectedDate);
    this.selectedDate = event.value;
    console.log(this.selectedDate);
  }

  get hospital() {
    return this.channellingForm.get('hospital');
  }

  get doctor() {
    return this.channellingForm.get('doctor');
  }

  get startTime() {
    return this.channellingForm.get('startTime');
  }

  get endTime() {
    return this.channellingForm.get('endTime');
  }

  get room() {
    return this.channellingForm.get('room');
  }

  get price() {
    return this.channellingForm.get('price');
  }

  save() {

    console.log(this.channellingForm.value);

    this.inProcessSave = true;
    if (this.channellingForm.valid) {
      console.log(this.channellingForm.value);
      this._channellingService.save(this.channellingForm.value).subscribe(
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
          this.inProcessSave = false;
        },
        error => {
          console.log(error);
          this.inProcessSave = false;
        }
      );
    }

  }

  loadDoctors() {
    this._doctorService.getAll().subscribe(
      response => {
        console.log(response);
        this.doctors = response;
        console.log(this.doctors);
      },
      error => {
        console.log(error);
      }
    );
  }

  getCats(event) {
    console.log(event);
    this._doctorService.getCats(event.value).subscribe(
      response => {
        if (response == null) {
          this._snackBar.open('Doctor\'s categoryies load failed ', '', {
            duration: 3000,
            panelClass: ['snackbar-success']
          });
        } else {

          this.categoryies = response;
          console.log(response);

        }
      },
      error => {
        this._snackBar.open('Doctor\'s categoryies load failed ', '', {
          duration: 3000,
          panelClass: ['snackbar-success']
        });
      }
    );
  }

  show(event){

    this.selectedDate = event.value;

    console.log(this.selectedDate.getMilliseconds());

    console.log(event.value);
  }

  test() {
    this._channellingService.test().subscribe(
      responce => {
        console.log(responce);
      },
      error => {
        console.log(error);
      }
    );
  }

}
