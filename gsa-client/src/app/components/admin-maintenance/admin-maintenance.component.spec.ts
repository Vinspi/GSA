import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminMaintenanceComponent } from './admin-maintenance.component';

describe('AdminMaintenanceComponent', () => {
  let component: AdminMaintenanceComponent;
  let fixture: ComponentFixture<AdminMaintenanceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminMaintenanceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminMaintenanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
