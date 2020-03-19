import { DoctorFormComponent } from './components/doctor-form/doctor-form.component';
import { HospitalFormComponent } from './components/hospital-form/hospital-form.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ChannellingFormComponent } from './components/channelling-form/channelling-form.component';


const routes: Routes = [
  {path: 'hospital', component: HospitalFormComponent},
  {path: 'doctor', component: DoctorFormComponent},
  {path: 'channelling', component: ChannellingFormComponent}


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const RoutingComponents = [HospitalFormComponent];
