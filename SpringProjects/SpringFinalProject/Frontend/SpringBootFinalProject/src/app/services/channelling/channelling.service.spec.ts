import { TestBed } from '@angular/core/testing';

import { ChannellingService } from './channelling.service';

describe('ChannellingService', () => {
  let service: ChannellingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChannellingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
