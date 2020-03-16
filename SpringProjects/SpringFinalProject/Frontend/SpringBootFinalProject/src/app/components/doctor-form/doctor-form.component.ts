import { CategoryService } from './../../services/category.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-doctor-form',
  templateUrl: './doctor-form.component.html',
  styleUrls: ['./doctor-form.component.css']
})
export class DoctorFormComponent implements OnInit {

  isEmpty = true;
  isLength = false;
  inProcess = false;
  catObj;

  categoryForm = this.fb.group({
    category: ['', [Validators.required, Validators.minLength(4)]]
  });

  constructor(private fb: FormBuilder, private _snackBar: MatSnackBar, private _categoryService: CategoryService) {}

  ngOnInit(): void {}

  get category() {
    return this.categoryForm.get('category');
  }

  addNewCat(cat: string) {
    console.log(cat);
    this.inProcess = true;
    if (cat === '') {
      this._snackBar.open('Please enter Category', '', {
        duration: 3000,
        panelClass: ['snackbar-error']
      });
    } else if (cat.length < 4) {
      this._snackBar.open('Category must be at least 4 characters', '', {
        duration: 3000,
        panelClass: ['snackbar-error']
      });
    } else if (!(/^[A-Za-z]{4,9}$/.test(cat))) {
      this._snackBar.open('Invalid Category', '', {
        duration: 3000,
        panelClass: ['snackbar-error']
      });
    } else {

      this.catObj = new Category(null, cat, null);
      console.log(this.catObj);
      this._categoryService.save(this.catObj).subscribe(
        response => {
          // console.log(response);
          if (response.success) {
            this._snackBar.open(response.message, '', {
              duration: 3000,
              panelClass: ['snackbar-success']
            });
          } else {
            this._snackBar.open(response.message, '', {
              duration: 3000,
              panelClass: ['snackbar-error']
            });
          }
          this.inProcess = false;
        },
        error => {
          console.log(error);
          this.inProcess = false;
        }
      );
    }
    this.inProcess = false;
  }


}

export class Category {

  constructor(public id: number, public category: string, public status: string) {}

}
