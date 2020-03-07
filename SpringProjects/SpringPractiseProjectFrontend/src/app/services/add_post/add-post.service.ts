import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {PostPayload} from '../../components/auth/add-post/post-payload';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AddPostService {

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
  }

}
