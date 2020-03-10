import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LocalStorageService} from 'ngx-webstorage';

@Injectable()
export class HttpClientIntercepter implements HttpInterceptor {

  constructor(private $localStorage: LocalStorageService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

<<<<<<< HEAD
    const token = this.$localStorage.retrieve('authenticationtoke');
=======
    const token = this.$localStorage.retrieve('authenticationToken');
>>>>>>> 915292cb496ecc187fee2cec86c649076bab19f4
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
