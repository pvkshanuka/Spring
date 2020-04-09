import { ClientUpdateFormComponent } from './../client-update-form/client-update-form.component';
import { AppointmentService } from './../../services/appointment/appointment.service';
import { DoctorService } from './../../services/doctor/doctor.service';
import { ClientService } from './../../services/client/client.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { DataService, UserDetails } from './../../services/data/data.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-manager-home',
  templateUrl: './manager-home.component.html',
  styleUrls: ['./manager-home.component.css']
})
export class ManagerHomeComponent implements OnInit {


  userData;

  userDetails: UserDetails;


  constructor(
    private data: DataService,
    public dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute,
    private _snackBar: MatSnackBar,
    private _clientService: ClientService,
    private _doctorService: DoctorService,
    private _appointmentService: AppointmentService
  ) {}

  ngOnInit(): void {
    this.data.userDetails.subscribe((user) => (this.userDetails = user));

    if (this.userDetails == null && this.userDetails.type === '2') {
      this.router.navigate(['../'], { relativeTo: this.route });
    }

    this.loadUserData();
  }

  openUpdateDialog() {
    this.dialog.open(ClientUpdateFormComponent, {
      height: 'fit',
      width: 'fit',
    });
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
            }

          },
          error => {
            console.log(error);
            this._snackBar.open('User data load error!', '', {
              duration: 3000,
              panelClass: ['snackbar-error']
            });
          }
        );
  }

}
