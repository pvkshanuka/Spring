import { HttpInterceptorService } from './services/httpInterceptor/http-interceptor.service';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule, RoutingComponents } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MaterialModule} from './material/material.module';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { ClientRegComponent } from './components/client-reg/client-reg.component';
import { ClientLoginComponent } from './components/client-login/client-login.component';
import { ChannellingSearchComponent } from './components/channelling-search/channelling-search.component';
import { DoctorFormComponent } from './components/doctor-form/doctor-form.component';
import { ChannellingFormComponent } from './components/channelling-form/channelling-form.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HomeComponent } from './components/home/home.component';
import { ClientHomeComponent } from './components/client-home/client-home.component';
import { FooterComponent } from './components/footer/footer.component';
import { ClientUpdateFormComponent } from './components/client-update-form/client-update-form.component';
import { ManagerHomeComponent } from './components/manager-home/manager-home.component';
import { ManagerRegComponent } from './components/manager-reg/manager-reg.component';
import { AdminHomeComponent } from './components/admin-home/admin-home.component';

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    ClientRegComponent,
    ClientLoginComponent,
    ChannellingSearchComponent,
    DoctorFormComponent,
    ChannellingFormComponent,
    RoutingComponents,
    HomeComponent,
    ClientHomeComponent,
    FooterComponent,
    ClientUpdateFormComponent,
    ManagerHomeComponent,
    ManagerRegComponent,
    AdminHomeComponent
  ],
  entryComponents: [ClientRegComponent, ClientLoginComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS, useClass: HttpInterceptorService, multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
