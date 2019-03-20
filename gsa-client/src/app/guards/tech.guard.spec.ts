import { TestBed, async, inject } from '@angular/core/testing';

import { TechGuard } from './tech.guard';

describe('TechGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TechGuard]
    });
  });

  it('should ...', inject([TechGuard], (guard: TechGuard) => {
    expect(guard).toBeTruthy();
  }));
});
