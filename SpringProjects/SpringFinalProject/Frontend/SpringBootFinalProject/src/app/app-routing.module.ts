import { ManagerHomeComponent } from './components/manager-home/manager-home.component';
import { ClientHomeComponent } from './components/client-home/client-home.component';
import { HomeComponent } from './components/home/home.component';
import { DoctorFormComponent } from './components/doctor-form/doctor-form.component';
import { HospitalFormComponent } from './components/hospital-form/hospital-form.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ChannellingFormComponent } from './components/channelling-form/channelling-form.component';


const routes: Routes = [
  {path: 'hospital', component: HospitalFormComponent},
  {path: 'doctor', component: DoctorFormComponent},
  {path: 'channelling', component: ChannellingFormComponent},
  {path: 'user', component: ClientHomeComponent},
  {path: 'manager', component: ManagerHomeComponent},

  // {path: '', component: HomeComponent},
  {path: 'home', component: HomeComponent}


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const RoutingComponents = [HospitalFormComponent];
