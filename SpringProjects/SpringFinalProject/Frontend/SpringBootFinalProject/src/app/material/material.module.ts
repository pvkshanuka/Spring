import { BrowserModule } from '@angular/platform-browser';
import {MatButtonModule} from '@angular/material/button';
import {NgModule} from '@angular/core';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatDialogModule} from '@angular/material/dialog';
import {MatTabsModule} from '@angular/material/tabs';
import {MatTableModule} from '@angular/material/table';
import {MatSortModule } from '@angular/material/sort';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatListModule} from '@angular/material/list';
import {MatSelectModule} from '@angular/material/select';
import {MatDatepickerModule, MatDatepickerInput} from '@angular/material/datepicker';
import {MatNativeDateModule} from '@angular/material/core';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
// import {MatDatepickerInputEvent} from '@angular/material/datepicker';



const MaterialComponents = [
  MatButtonModule,
  MatToolbarModule,
  MatIconModule,
  MatGridListModule,
  MatFormFieldModule,
  MatInputModule,
  FormsModule,
  ReactiveFormsModule,
  MatDialogModule,
  MatTabsModule,
  MatTableModule,
  MatSortModule,
  BrowserModule,
  MatPaginatorModule,
  MatListModule,
  MatSelectModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatAutocompleteModule,
  MatSnackBarModule,
  OwlDateTimeModule,
  OwlNativeDateTimeModule
  // MatDatepickerInputEvent
];

@NgModule({
  imports: [
    MaterialComponents
  ],
  exports: [
    MaterialComponents
  ]

})
export class MaterialModule {
}
