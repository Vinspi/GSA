import { TestBed, async, inject } from '@angular/core/testing';

import { MaintenanceGuard } from './maintenance.guard';

describe('MaintenanceGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MaintenanceGuard]
    });
  });

  it('should ...', inject([MaintenanceGuard], (guard: MaintenanceGuard) => {
    expect(guard).toBeTruthy();
  }));
});
