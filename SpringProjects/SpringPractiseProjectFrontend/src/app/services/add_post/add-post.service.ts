<<<<<<< HEAD
import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {PostPayload} from '../../components/auth/add-post/post-payload';
=======
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {PostPayload} from '../../components/auth/add-post/post-payload';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';
>>>>>>> 915292cb496ecc187fee2cec86c649076bab19f4

@Injectable({
  providedIn: 'root'
})
export class AddPostService {

<<<<<<< HEAD
  constructor(private httpClient: HttpClient) { }

  addPost(postPayload: PostPayload) {
    return this.httpClient.post('http://localhost:8181/api/post', postPayload);
  }

  getAllPosts(): Observable<Array<PostPayload>> {
    return this.httpClient.get<Array<PostPayload>>('http://localhost:8181/api/post');
=======
  constructor(private httpClient: HttpClient, private router: Router, private authService: AuthService) {
  }

  addPost(postPayload: PostPayload) {
    if (this.authService.isAuthenticated()) {
      return this.httpClient.post('http://localhost:8181/api/post/create', postPayload);
    } else {
      this.router.navigateByUrl('/login');
    }
  }

  getAllPosts(): Observable<Array<PostPayload>> {
    return this.httpClient.get<Array<PostPayload>>('http://localhost:8181/api/post/all');
  }

  getPost(paramLink: number): Observable<PostPayload> {
    return this.httpClient.get<PostPayload>('http://localhost:8181/api/post/get/' + paramLink);
>>>>>>> 915292cb496ecc187fee2cec86c649076bab19f4
  }

}
