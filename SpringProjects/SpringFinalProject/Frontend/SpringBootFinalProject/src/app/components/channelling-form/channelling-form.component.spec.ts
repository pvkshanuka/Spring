import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChannellingFormComponent } from './channelling-form.component';

describe('ChannellingFormComponent', () => {
  let component: ChannellingFormComponent;
  let fixture: ComponentFixture<ChannellingFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChannellingFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChannellingFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
