import { ClientService } from './../../services/client/client.service';
import { ClientUpdateFormComponent } from './../client-update-form/client-update-form.component';
import { MatTableDataSource } from '@angular/material/table';
import { AppointmentService } from './../../services/appointment/appointment.service';
import { HospitalService } from './../../services/hospital/hospital.service';
import { DoctorService } from 'src/app/services/doctor/doctor.service';
import { CategoryService } from './../../services/category/category.service';
import { ChannellingService } from './../../services/channelling/channelling.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { UserDetails, DataService } from './../../services/data/data.service';
import { ChannellingDTO } from './../../DTOs/channelling-dto';
import { MatPaginator } from '@angular/material/paginator';
import { Component, OnInit, ViewChild } from '@angular/core';
import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { AppointmentSearchDTO } from 'src/app/DTOs/appointmentSearch-dto';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-client-home',
  templateUrl: './client-home.component.html',
  styleUrls: ['./client-home.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition(
        'expanded <=> collapsed',
        animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')
      ),
    ]),
  ],
})
export class ClientHomeComponent implements OnInit {
  @ViewChild(MatPaginator) paginator: MatPaginator;

  DATA_ROWS: ChannellingDTO[];
  dataSource;
  STATUS;
  DOCTORS;

  selected_sta;
  selected_doc;
  selected_date;

  userData;

  dateNow = new Date();

  // columnsToDisplay = ['name', 'weight', 'symbol', 'position'];
  columnsToDisplay = ['doctor', 'hospital', 'price', 'startTime'];

  expandedElement: ChannellingDTO | null;

  appointmentSearchDTO: AppointmentSearchDTO;

  userDetails: UserDetails;

  inProcessSave = false;

  constructor(
    private data: DataService,
    public dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute,
    private _snackBar: MatSnackBar,
    private _channellingService: ChannellingService,
    private _categoryService: CategoryService,
    private _clientService: ClientService,
    private _doctorService: DoctorService,
    private _hospitalService: HospitalService,
    private _appointmentService: AppointmentService
  ) {}

  ngOnInit(): void {
    this.data.userDetails.subscribe((user) => (this.userDetails = user));

    if (this.userDetails == null) {
      this.router.navigate(['../'], { relativeTo: this.route });
    }

    this.dateNow.setDate(this.dateNow.getDate() - 1);

    this.loadData();

    this.loadDoctors();

    this.loadUserData();
  }

  openUpdateDialog() {
    this.dialog.open(ClientUpdateFormComponent, {
      height: 'fit',
      width: 'fit',
    });
  }

  loadData() {
    console.log(this.selected_doc);

    this.appointmentSearchDTO = new AppointmentSearchDTO(
      this.userDetails.id,
      parseInt(this.selected_doc),
      new Date(this.selected_date),
      this.selected_sta
    );

    console.log(this.appointmentSearchDTO);

    this._appointmentService.loadAllByUser(this.appointmentSearchDTO).subscribe(
      (response) => {
        console.log(response);
        if (response) {
          this.DATA_ROWS = response;
          this.dataSource = new MatTableDataSource(this.DATA_ROWS);
          this.dataSource.paginator = this.paginator;
        } else {
          this.dataSource = null;
          this._snackBar.open('No data to show.', '', {
            duration: 3000,
            panelClass: ['snackbar-warning'],
          });
        }
      },
      (error) => {
        console.log(error);
        // this.inProcess = false;
      }
    );
  }

  deleteAppoinment(id) {
    console.log('awaaa');


    const snackBarAlt = this._snackBar.open('Are you sure you want to delete Appointment?', 'Yes', {
      duration: 3000,
      panelClass: ['snackbar-confirm'],
    });

    snackBarAlt.onAction().subscribe(() => {
      this.inProcessSave = true;
      this._appointmentService.delete(id).subscribe(
        (response) => {
          // console.log(response);
          if (response.success) {
            this._snackBar.open(response.message, '', {
              duration: 3000,
              panelClass: ['snackbar-success'],
            });
            this.loadData();
          } else {
            this._snackBar.open(response.message, '', {
              duration: 3000,
              panelClass: ['snackbar-error'],
            });
          }
          this.inProcessSave = false;
        },
        (error) => {
          console.log(error);
          this.inProcessSave = false;
        }
      );
    });


  }

  loadDoctors() {
    this._doctorService.getAll().subscribe(
      (response) => {
        this.DOCTORS = response;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  resetSearch() {
    this.selected_sta = null;
    this.selected_doc = '';
    this.selected_date = '';

    this.loadData();
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
