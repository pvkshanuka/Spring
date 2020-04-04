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
  trigger
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
      )
    ])
  ]
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
    private _doctorService: DoctorService,
    private _hospitalService: HospitalService,
    private _appointmentService: AppointmentService
  ) {}

  ngOnInit(): void {

    this.data.userDetails.subscribe(user => this.userDetails = user);

    if(this.userDetails == null) {
      this.router.navigate(['../'], { relativeTo: this.route });
    }



    this.dateNow.setDate(this.dateNow.getDate() - 1);

    this.loadData();

    // this.loadDoctors();

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

  // loadDoctors() {
  //   this._doctorService.getAll().subscribe(
  //     response => {
  //       // console.log(response);
  //       this.DOCTORS = response;
  //     },
  //     error => {
  //       console.log(error);
  //     }
  //   );
  // }

  resetSearch() {
    this.selected_sta = '';
    this.selected_doc = '';
    this.selected_date = '';

    this.loadData();

  }

}
