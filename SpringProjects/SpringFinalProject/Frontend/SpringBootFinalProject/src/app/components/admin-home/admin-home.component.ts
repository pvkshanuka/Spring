import { ManagerRegComponent } from './../manager-reg/manager-reg.component';
import { ClientUpdateFormComponent } from './../client-update-form/client-update-form.component';
import { MatTableDataSource } from '@angular/material/table';
import { HospitalService } from './../../services/hospital/hospital.service';
import { DoctorService } from 'src/app/services/doctor/doctor.service';
import { ClientService } from './../../services/client/client.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { ChannellingDTO } from './../../DTOs/channelling-dto';
import { MatPaginator } from '@angular/material/paginator';
import { HospitalDTO } from './../../DTOs/hospital-dto';
import { UserDetails, DataService } from './../../services/data/data.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition(
        'expanded <=> collapsed',
        animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')
      )
    ])
  ]
})
export class AdminHomeComponent implements OnInit {


  @ViewChild(MatPaginator) paginator: MatPaginator;

  DATA_ROWS: HospitalDTO[];
  dataSource;

  hos_search_name = '';


  // columnsToDisplay = ['name', 'weight', 'symbol', 'position'];
  columnsToDisplay = ['id', 'name', 'city', 'status'];

  userDetails: UserDetails;

  expandedElement: HospitalDTO | null;

  // channellingSearchDTO: ChannellingSearchDTO;

  inProcessSave = false;

  userData;


  constructor(
    private data: DataService,
    public dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute,
    private _snackBar: MatSnackBar,
    private _clientService: ClientService,
    private _doctorService: DoctorService,
    private _hospitalService: HospitalService
    // private _categoryService: CategoryService,
    // private _appointmentService: AppointmentService
  ) { }

  ngOnInit(): void {

    this.data.userDetails.subscribe((user) => (this.userDetails = user));

    if (this.userDetails == null || this.userDetails.type != 1) {
      this.router.navigate(['../'], { relativeTo: this.route });
    }

    this.loadData();

    this.loadUserData();

  }

  openUpdateDialog() {
    this.dialog.open(ClientUpdateFormComponent, {
      height: 'fit',
      width: 'fit',
    });
  }

  openAdminRegDialog() {
    this.dialog.open(ManagerRegComponent, {
      height: 'fit',
      width: 'fit',
    });
  }

  openAddDoctorDialog() {
    console.log('openAddDoctorDialog');
  }

  openAddHospitalDialog() {
    console.log('openAddHospitalDialog');
  }

  resetSearch() {
    this.hos_search_name = '';

    this.loadData();

  }

  hospitalDelete(id){
    console.log('hospitalDelete : ' + id);

    const snackBarAlt = this._snackBar.open('Are you sure you want to Delete Hospital?', 'Yes', {
      duration: 3000,
      panelClass: ['snackbar-confirm'],
    });

    snackBarAlt.onAction().subscribe(() => {

      console.log('Deleting : ' + id);
      this.inProcessSave = true;
      this._hospitalService.delete(id).subscribe(
          response => {
            // console.log(response);
            if (response.success) {
              this._snackBar.open(response.message, '', {
                duration: 3000,
                panelClass: ['snackbar-success']
              });
              this.loadData();
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
            this._snackBar.open('Delete error!', '', {
              duration: 3000,
              panelClass: ['snackbar-error']
            });
          }
        );
      });

  }

  resetPassword(id){
    console.log('resetPassword : ' + id);

    const snackBarAlt = this._snackBar.open('Are you sure you want to Rest Password of Manager?', 'Yes', {
      duration: 3000,
      panelClass: ['snackbar-confirm'],
    });

    snackBarAlt.onAction().subscribe(() => {
      this.inProcessSave = true;
      this._clientService.resetPassword(id).subscribe(
          response => {
            // console.log(response);
            if (response.success) {
              this._snackBar.open(response.message, '', {
                duration: 3000,
                panelClass: ['snackbar-success']
              });
              this.loadData();
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
            this._snackBar.open('Password Reset error!', '', {
              duration: 3000,
              panelClass: ['snackbar-error']
            });
          }
        );
      });

  }

  // hospitalDisable(id){
  //   console.log('hospitalDisable : ' + id);
  // }

  // hospitalEnable(id){
  //   console.log('hospitalEnable : ' + id);
  // }

  addUser(id){
    console.log('addUser : ' + id);
    this.dialog.open(ManagerRegComponent, {
      height: 'fit',
      width: 'fit',
      data: {
        hospital: id
      }
    });
    this.loadData();
  }

  deleteManager(id){
    console.log('deleteManager : ' + id);

    const snackBarAlt = this._snackBar.open('Are you sure you want to Delete Manager?', 'Yes', {
      duration: 3000,
      panelClass: ['snackbar-confirm'],
    });

    snackBarAlt.onAction().subscribe(() => {

      console.log('Deleting : ' + id);
      this.inProcessSave = true;
      this._clientService.delete(id).subscribe(
          response => {
            // console.log(response);
            if (response.success) {
              this._snackBar.open(response.message, '', {
                duration: 3000,
                panelClass: ['snackbar-success']
              });
              this.loadData();
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
            this._snackBar.open('Delete error!', '', {
              duration: 3000,
              panelClass: ['snackbar-error']
            });
          }
        );
      });

  }

  loadData() {

    // console.log(this.selected_doc);



    console.log(this.hos_search_name);

    this._hospitalService.findByNameStartsWith(this.hos_search_name).subscribe(
      response => {
        console.log(response);
        this.DATA_ROWS = response;
        this.dataSource = new MatTableDataSource(this.DATA_ROWS);
        this.dataSource.paginator = this.paginator;
      },
      error => {
        console.log(error);
        // this.inProcess = false;
      }
    );
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
