import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterSuccessComponent } from './components/auth/register-success/register-success.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {RouterModule} from '@angular/router';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {Ng2Webstorage} from 'ngx-webstorage';
import { HomeComponent } from './components/home/home.component';
import { AddPostComponent } from './components/auth/add-post/add-post.component';
import {EditorModule} from '@tinymce/tinymce-angular';
import {HttpClientIntercepter} from './components/auth/http-client-intercepter';
<<<<<<< HEAD
=======
import { PostComponent } from './components/post/post.component';
import {AuthGuard} from './gurd/auth.guard';
>>>>>>> 915292cb496ecc187fee2cec86c649076bab19f4

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    RegisterComponent,
    LoginComponent,
    RegisterSuccessComponent,
    HomeComponent,
<<<<<<< HEAD
    AddPostComponent
=======
    AddPostComponent,
    PostComponent
>>>>>>> 915292cb496ecc187fee2cec86c649076bab19f4
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    EditorModule,
    Ng2Webstorage.forRoot(),
    RouterModule.forRoot([
      {path: '', component: HomeComponent},
      {path: 'home', component: HomeComponent},
      {path: 'register', component: RegisterComponent},
<<<<<<< HEAD
      {path: 'login', component: LoginComponent},
      {path: 'add-post', component: AddPostComponent},
=======
      {path: 'post/:id', component: PostComponent},
      {path: 'login', component: LoginComponent},
      {path: 'add-post', component: AddPostComponent, canActivate: [AuthGuard]},
>>>>>>> 915292cb496ecc187fee2cec86c649076bab19f4
      {path: 'register-success', component: RegisterSuccessComponent}
    ])
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: HttpClientIntercepter, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
