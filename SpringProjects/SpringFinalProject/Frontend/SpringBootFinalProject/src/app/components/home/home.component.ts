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

  dateNow = new Date();

  // columnsToDisplay = ['name', 'weight', 'symbol', 'position'];
  columnsToDisplay = ['doctor', 'hospital', 'price', 'startTime'];

  expandedElement: ChannellingDTO | null;

  channellingSearchDTO: ChannellingSearchDTO;

  constructor(
    private _channellingService: ChannellingService,
    private _categoryService: CategoryService,
    private _doctorService: DoctorService,
    private _hospitalService: HospitalService
  ) {}

  ngOnInit() {


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

    this.channellingSearchDTO = new ChannellingSearchDTO(
      parseInt(( document.getElementById('search_cat') as HTMLInputElement).value),
      parseInt(( document.getElementById('search_hos') as HTMLInputElement).value),
      parseInt(( document.getElementById('search_doc') as HTMLInputElement).value),
      new Date(( document.getElementById('search_date') as HTMLInputElement).value),
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

}

