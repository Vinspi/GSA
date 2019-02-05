import { TestBed } from '@angular/core/testing';

import { LoremServiceService } from './lorem-service.service';

describe('LoremServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: LoremServiceService = TestBed.get(LoremServiceService);
    expect(service).toBeTruthy();
  });
});
