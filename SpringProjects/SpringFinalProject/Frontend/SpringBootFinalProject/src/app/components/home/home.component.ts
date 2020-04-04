import { MatSnackBar } from '@angular/material/snack-bar';
import { AppointmnentDTO } from './../../DTOs/appointment-dto';
import { MatDialog } from '@angular/material/dialog';
import { ClientRegComponent } from './../client-reg/client-reg.component';
import { ClientLoginComponent } from './../client-login/client-login.component';
import { DataService, UserDetails } from './../../services/data/data.service';
import { AppointmentService } from './../../services/appointment/appointment.service';
import { ChannellingSearchDTO } from './../../DTOs/channellingSearch-dto';
import { Channelling } from './../channelling-search/channelling-search.component';
import { MatPaginator } from '@angular/material/paginator';
import { HospitalService } from './../../services/hospital/hospital.service';
import { Category } from './../../modles/category-model';
import { CategoryService } from './../../services/category/category.service';
import { ChannellingDTO } from './../../DTOs/channelling-dto';
import { ChannellingService } from './../../services/channelling/channelling.service';
import { MatTableDataSource } from '@angular/material/table';
import { Component, OnInit, ViewChild } from '@angular/core';
import {
  animate,
  state,
  style,
  transition,
  trigger
} from '@angular/animations';
import { DoctorService } from 'src/app/services/doctor/doctor.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
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
export class HomeComponent implements OnInit {

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


  dateNow = new Date();

  // columnsToDisplay = ['name', 'weight', 'symbol', 'position'];
  columnsToDisplay = ['doctor', 'hospital', 'price', 'startTime'];

  expandedElement: ChannellingDTO | null;

  channellingSearchDTO: ChannellingSearchDTO;

  userDetails: UserDetails;

  inProcessSave = false;

  constructor(
    private data: DataService,
    public dialog: MatDialog,
    private _snackBar: MatSnackBar,
    private _channellingService: ChannellingService,
    private _categoryService: CategoryService,
    private _doctorService: DoctorService,
    private _hospitalService: HospitalService,
    private _appointmentService: AppointmentService
  ) {}

  ngOnInit() {

    this.data.userDetails.subscribe(user => this.userDetails = user);

    this.dateNow.setDate(this.dateNow.getDate() - 1);

    this.loadData();

    this.loadCategorys();

    this.loadDoctors();

    this.loadHospitals();

  }

  // applyFilter(event: Event) {
  //   const filterValue = (event.target as HTMLInputElement).value;
  // }

  loadData() {

    console.log(this.selected_doc);

    this.channellingSearchDTO = new ChannellingSearchDTO(
      parseInt(this.selected_cat),
      parseInt(this.selected_hos),
      parseInt(this.selected_doc),
      new Date(this.selected_date),
    );

    console.log(this.channellingSearchDTO);

    this._channellingService.loadAll(this.channellingSearchDTO).subscribe(
      response => {
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

  loadHospitals() {
    this._hospitalService.getAll().subscribe(
      response => {
        // console.log(response);
        this.HOSPITALS = response;
      },
      error => {
        console.log(error);
      }
    );
  }

  resetSearch() {
    this.selected_cat = '';
    this.selected_doc = '';
    this.selected_hos = '';
    this.selected_date = '';

    this.loadData();

  }

  addAppoinment(id: number) {

    if (this.userDetails && this.userDetails.id) {



    // this.inProcessSave = true;
      this._appointmentService.save(new AppointmnentDTO(null, this.userDetails.id, id, new Date())).subscribe(
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

  } else {
this.openLoginDialog();
  }

  }

  openSignUpDialog() {
    this.dialog.open(ClientRegComponent, {
      height: 'fit',
      width: 'fit',
    });
  }

  openLoginDialog() {
    this.dialog.open(ClientLoginComponent, {
      height: 'fit',
      width: 'fit',
    });
  }

}

