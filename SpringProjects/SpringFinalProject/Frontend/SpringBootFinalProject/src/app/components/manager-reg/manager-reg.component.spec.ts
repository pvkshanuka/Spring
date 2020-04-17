import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerRegComponent } from './manager-reg.component';

describe('ManagerRegComponent', () => {
  let component: ManagerRegComponent;
  let fixture: ComponentFixture<ManagerRegComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManagerRegComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagerRegComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
