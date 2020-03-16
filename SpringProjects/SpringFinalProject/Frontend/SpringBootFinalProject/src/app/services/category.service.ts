import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  _url = 'http://localhost:8030/category';

  constructor(private _http: HttpClient) { }

  save(category) {
    return this._http.post<any>(this._url, category);
  }

}
