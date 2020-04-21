import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CategoryDTO } from './../../DTOs/category-dto';
import { DoctorService } from './../../services/doctor/doctor.service';
import { CategoryService } from '../../services/category/category.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit, NgModule, Inject } from '@angular/core';
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
  inProcessSave = false;

  catObj;

  categortDTO: CategoryDTO;

  categorys;

  doctorData;

  doc_id;
  doc_name;
  doc_contact;
  doc_cats;

  categoryForm = this.fb.group({
    category: ['', [Validators.required, Validators.minLength(4)]]
  });

  doctorForm = this.fb.group({
    id: [],
    name: ['', [Validators.required, Validators.minLength(4)]],
    contact: ['', [Validators.required, Validators.pattern('^[0-9]{9}$')]],
    doctorCategories: []
  });

  constructor(
    @Inject(MAT_DIALOG_DATA) dialogData,
    private fb: FormBuilder,
    private _snackBar: MatSnackBar,
    private _categoryService: CategoryService,
    private _doctorService: DoctorService
  ) {

    console.log('>>>>>>>>>>>>>>>>>>>');
    console.log(dialogData);

    this.doctorData = dialogData;

    this.doc_id = dialogData.doctor.id;

    this.doc_name = dialogData.doctor.name;
    this.doc_contact = dialogData.doctor.contact;

  }

  ngOnInit(): void {
    this.loadCategorys();
  }

  get category() {
    return this.categoryForm.get('category');
  }

  get name() {
    return this.doctorForm.get('name');
  }

  get contact() {
    return this.doctorForm.get('contact');
  }

  get categories() {
    return this.doctorForm.get('doctorCategories');
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

  save() {
    this.inProcessSave = true;
    if (this.doctorForm.valid) {
      console.log(this.doctorForm.value);

      const cats = this.categories.value;

      if (cats != null) {
        const catArray = [];

        for (let i = 0; i < cats.length; i++) {
          catArray.push({
            categoryid: cats[i]
          });
        }

        this.doctorForm.controls.doctorCategories.setValue(catArray);

        console.log('>>>>>>>>>>>>>>>');
        console.log(this.doctorForm.value);
      } else {
        this.doctorForm.controls.doctorCategories.setValue(null);
      }

      this._doctorService.save(this.doctorForm.value).subscribe(
        response => {
          // console.log(response);
          if (response.success) {
            this._snackBar.open(response.message, '', {
              duration: 3000,
              panelClass: ['snackbar-success']
            });

            this.doctorForm.reset();

          } else {
            this._snackBar.open(response.message, '', {
              duration: 3000,
              panelClass: ['snackbar-error']
            });
          }
        },
        error => {
          console.log(error);
        }
      );
    }

    this.inProcessSave = false;
  }

  update() {
    this.inProcessSave = true;
    if (this.doctorForm.valid) {
      this.doctorForm.value.id=this.doc_id;

      const cats = this.categories.value;

      if (cats != null) {
        const catArray = [];

        for (let i = 0; i < cats.length; i++) {
          catArray.push({
            categoryid: cats[i]
          });
        }

        this.doctorForm.controls.doctorCategories.setValue(catArray);

      } else {
        this.doctorForm.controls.doctorCategories.setValue(null);
      }
      this.doctorForm.value.id=this.doc_id;
      this._doctorService.update(this.doctorForm.value).subscribe(
        response => {
          // console.log(response);
          if (response.success) {
            this._snackBar.open(response.message, '', {
              duration: 3000,
              panelClass: ['snackbar-success']
            });

            this.doctorForm.reset();

          } else {
            this._snackBar.open(response.message, '', {
              duration: 3000,
              panelClass: ['snackbar-error']
            });
          }
        },
        error => {
          console.log(error);
        }
      );
    }

    this.inProcessSave = false;
  }

  test() {
    this._doctorService.test().subscribe(
      responce => {
        console.log(responce);
      },
      error => {
        console.log(error);
      }
    );
  }
}
