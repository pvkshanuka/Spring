import { Category } from './../../modles/category-model';
import { CategoryService } from './../../services/category/category.service';
import { ChannellingSearchDTO } from './../../DTOs/channellingSearch-dto';
import { MatTableDataSource } from '@angular/material/table';
import { ChannellingDTO } from './../../DTOs/channelling-dto';
import { MatPaginator } from '@angular/material/paginator';
import { AppointmentSearchDTO } from './../../DTOs/appointmentSearch-dto';
import { ClientUpdateFormComponent } from './../client-update-form/client-update-form.component';
import { AppointmentService } from './../../services/appointment/appointment.service';
import { DoctorService } from './../../services/doctor/doctor.service';
import { ClientService } from './../../services/client/client.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { DataService, UserDetails } from './../../services/data/data.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ChannellingService } from 'src/app/services/channelling/channelling.service';
import {
  animate,
  state,
  style,
  transition,
  trigger
} from '@angular/animations';

@Component({
  selector: 'app-manager-home',
  templateUrl: './manager-home.component.html',
  styleUrls: ['./manager-home.component.css'],
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
export class ManagerHomeComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;

  DATA_ROWS: ChannellingDTO[];
  dataSource;
  CATEGORYS;
  DOCTORS;
  HOSPITALS;

  selected_cat;
  selected_doc;
  selected_hos;
  selected_date;


  // columnsToDisplay = ['name', 'weight', 'symbol', 'position'];
  columnsToDisplay = ['doctor', 'hospital', 'price', 'startTime', 'status'];

  expandedElement: ChannellingDTO | null;

  channellingSearchDTO: ChannellingSearchDTO;

  inProcessSave = false;

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
    private _channellingService: ChannellingService,
    private _categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    this.data.userDetails.subscribe((user) => (this.userDetails = user));

    if (this.userDetails == null || this.userDetails.type != 2) {
      this.router.navigate(['../'], { relativeTo: this.route });
    }

    this.loadData();

    this.loadCategorys();

    this.loadDoctors();

    this.loadUserData();
  }


  loadData() {

    // console.log(this.selected_doc);

    this.channellingSearchDTO = new ChannellingSearchDTO(
      parseInt(this.selected_cat),
      1,
      parseInt(this.selected_doc),
      new Date(this.selected_date),
    );

    console.log(this.channellingSearchDTO);

    this._channellingService.loadAllByHospital(this.channellingSearchDTO).subscribe(
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

  loadCategorys() {
    this._categoryService.get(new Category(null, null, null)).subscribe(
      response => {
        // console.log(response);
        this.CATEGORYS = response;
        // console.log(this.categorys);
      },
      error => {
        console.log(error);
        // this.inProcess = false;
      }
    );
  }

  loadDoctors() {
    this._doctorService.getAll().subscribe(
      response => {
        // console.log(response);
        this.DOCTORS = response;
      },
      error => {
        console.log(error);
      }
    );
  }


  addAppoinment(id: number) {

//     if (this.userDetails && this.userDetails.id) {



//     // this.inProcessSave = true;
//       this._appointmentService.save(new AppointmnentDTO(null, this.userDetails.id, id, new Date())).subscribe(
//         response => {
//           // console.log(response);
//           if (response.success) {
//             this._snackBar.open(response.message, '', {
//               duration: 3000,
//               panelClass: ['snackbar-success']
//             });
//           } else {
//             this._snackBar.open(response.message, '', {
//               duration: 3000,
//               panelClass: ['snackbar-error']
//             });
//           }
//           this.inProcessSave = false;
//         },
//         error => {
//           console.log(error);
//           this.inProcessSave = false;
//         }
//       );

//   } else {
// this.openLoginDialog();
//   }

  }

  resetSearch() {
    this.selected_cat = '';
    this.selected_doc = '';
    this.selected_date = '';

    this.loadData();

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

  channellingDelete(id) {

    const snackBarAlt = this._snackBar.open('Are you sure you want to Delete Channelling?', 'Yes', {
      duration: 3000,
      panelClass: ['snackbar-confirm'],
    });

    snackBarAlt.onAction().subscribe(() => {

    console.log('Deleting : ' + id);
    this.inProcessSave = true;
    this._channellingService.delete(id).subscribe(
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

  channellingStart(id) {

    const snackBarAlt = this._snackBar.open('Are you sure you want to Start Channelling?', 'Yes', {
      duration: 3000,
      panelClass: ['snackbar-confirm'],
    });

    snackBarAlt.onAction().subscribe(() => {

    console.log('Deleting : ' + id);
    this.inProcessSave = true;
    this._channellingService.start(id).subscribe(
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
          this._snackBar.open('Start error!', '', {
            duration: 3000,
            panelClass: ['snackbar-error']
          });
        }
      );
    });
  }

  channellingFinish(id) {

    const snackBarAlt = this._snackBar.open('Are you sure you want to Finish Channelling?', 'Yes', {
      duration: 3000,
      panelClass: ['snackbar-confirm'],
    });

    snackBarAlt.onAction().subscribe(() => {

    console.log('Deleting : ' + id);
    this.inProcessSave = true;
    this._channellingService.finish(id).subscribe(
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
          this._snackBar.open('Finish error!', '', {
            duration: 3000,
            panelClass: ['snackbar-error']
          });
        }
      );
    });
  }

}
