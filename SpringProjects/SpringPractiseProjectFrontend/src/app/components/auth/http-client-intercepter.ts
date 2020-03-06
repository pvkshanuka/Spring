import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LocalStorageService} from 'ngx-webstorage';

@Injectable()
export class HttpClientIntercepter implements HttpInterceptor {

  constructor(private $localStorage: LocalStorageService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const token = this.$localStorage.retrieve('authenticationtoke');
    console.log('jwt token ' + token);

    if (token) {
      const cloned = req.clone({
        headers: req.headers.set('Authorization', 'Bearer ' + token)
      });

      return next.handle(cloned);

    } else {

      return next.handle(req);

    }

  }

}
