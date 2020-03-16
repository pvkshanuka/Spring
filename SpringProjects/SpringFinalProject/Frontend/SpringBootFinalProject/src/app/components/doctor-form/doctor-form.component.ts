import { CategoryService } from './../../services/category.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit, NgModule } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Category } from 'src/app/modles/category-model';


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

  categorys;

  categoryForm = this.fb.group({
    category: ['', [Validators.required, Validators.minLength(4)]]
  });

  constructor(
    private fb: FormBuilder,
    private _snackBar: MatSnackBar,
    private _categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    this.loadCategorys();
  }

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
    } else if (!/^[A-Za-z]{4,}$/.test(cat)) {
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
            this.loadCategorys();
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

  loadCategorys() {
    this._categoryService.get(new Category(null, null, null)).subscribe(
      response => {
        console.log(response);
        this.categorys = response;
        console.log(this.categorys);
      },
      error => {
        console.log(error);
        this.inProcess = false;
      }
    );
  }
}
