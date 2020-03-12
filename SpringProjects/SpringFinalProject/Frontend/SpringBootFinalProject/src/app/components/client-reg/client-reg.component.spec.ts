import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientRegComponent } from './client-reg.component';

describe('ClientRegComponent', () => {
  let component: ClientRegComponent;
  let fixture: ComponentFixture<ClientRegComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClientRegComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientRegComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
