import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {AddPostService} from '../../services/add_post/add-post.service';
import {PostPayload} from '../auth/add-post/post-payload';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  post: PostPayload;
  paramLink: number;

  constructor(private router: ActivatedRoute, private postService: AddPostService) {
    this.post = {
      id: '',
      content: '',
      title: '',
      username: ''
    };
  }

  ngOnInit(): void {
    this.router.params.subscribe(params => {
      this.paramLink = params.id;
    });

    this.postService.getPost(this.paramLink).subscribe((data: PostPayload) => {
      console.log('title : ' + data.title);
      this.post = data;
    }, (error: any) => {
      console.log('Failure Response');
    });

  }

}
