import { TestBed } from '@angular/core/testing';

import { AliquotService } from './aliquot.service';

describe('AliquotService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AliquotService = TestBed.get(AliquotService);
    expect(service).toBeTruthy();
  });
});
