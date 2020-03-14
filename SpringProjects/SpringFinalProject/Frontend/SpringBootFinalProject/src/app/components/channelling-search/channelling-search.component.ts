import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatSort } from '@angular/material/sort';

export interface Channelling {
  id: number;
  hospital: string;
  city: string;
  start: string;
  price: number;
  doctor: string;
  dtype: string;
}

const ELEMENT_DATA: Channelling[] = [
  { id: 1, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 2, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 3, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 4, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 5, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 6, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 7, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 8, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 9, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 10, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 53, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 54, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 55, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 56, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 57, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 58, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 59, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 60, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 61, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 62, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 63, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 64, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 65, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 66, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 67, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 68, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 69, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 70, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 71, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 72, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 73, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 74, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 75, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 76, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 77, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 78, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 79, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 80, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 81, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 82, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 83, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 84, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 85, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 86, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 87, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 88, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 89, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 90, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 91, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 92, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' },
  { id: 93, hospital: 'Nawaloka',
   city: 'Negombo', start: '16-03-2020 08:15 AM', price: 1200.00, doctor: 'Lalith Perera', dtype: 'Nurologist' }];


@Component({
  selector: 'app-channelling-search',
  templateUrl: './channelling-search.component.html',
  styleUrls: ['./channelling-search.component.css']
})
export class ChannellingSearchComponent implements OnInit, AfterViewInit {

  minDate = new Date();
  displayedColumns: string[] = ['hospital', 'doctor', 'start', 'price'];
  dataSource = new MatTableDataSource(ELEMENT_DATA);

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  ngOnInit(): void {
    this.dataSource.sort = this.sort;
  }

  onMatSortChange() {
    this.dataSource.sort = this.sort;
  }
}
