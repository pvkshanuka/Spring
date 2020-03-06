import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {PostPayload} from '../../components/auth/add-post/post-payload';

@Injectable({
  providedIn: 'root'
})
export class AddPostService {

  constructor(private httpClient: HttpClient) { }

  addPost(postPayload: PostPayload) {
    return this.httpClient.post('http://localhost:8181/api/post/create', postPayload);
  }

  getAllPosts(): Observable<Array<PostPayload>> {
    return this.httpClient.get<Array<PostPayload>>('http://localhost:8181/api/post/all');
  }

}
