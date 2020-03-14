import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MaterialModule} from './material/material.module';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { ClientRegComponent } from './components/client-reg/client-reg.component';
import { ClientLoginComponent } from './components/client-login/client-login.component';
import { ChannellingSearchComponent } from './components/channelling-search/channelling-search.component';
import { HospitalFormComponent } from './components/hospital-form/hospital-form.component';
import { DoctorFormComponent } from './components/doctor-form/doctor-form.component';
import { ChannellingFormComponent } from './components/channelling-form/channelling-form.component';

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    ClientRegComponent,
    ClientLoginComponent,
    ChannellingSearchComponent,
    HospitalFormComponent,
    DoctorFormComponent,
    ChannellingFormComponent
  ],
  entryComponents: [ClientRegComponent, ClientLoginComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
