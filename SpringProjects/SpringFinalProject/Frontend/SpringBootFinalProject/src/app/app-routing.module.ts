import { DoctorFormComponent } from './components/doctor-form/doctor-form.component';
import { HospitalFormComponent } from './components/hospital-form/hospital-form.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [
  {path: 'hospital', component: HospitalFormComponent},
  {path: 'doctor', component: DoctorFormComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const RoutingComponents = [HospitalFormComponent];
