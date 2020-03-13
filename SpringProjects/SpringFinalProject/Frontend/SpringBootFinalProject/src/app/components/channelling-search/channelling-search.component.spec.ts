import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChannellingSearchComponent } from './channelling-search.component';

describe('ChannellingSearchComponent', () => {
  let component: ChannellingSearchComponent;
  let fixture: ComponentFixture<ChannellingSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChannellingSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChannellingSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
