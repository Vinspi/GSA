import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OutdatedManagementComponent } from './outdated-management.component';

describe('OutdatedManagementComponent', () => {
  let component: OutdatedManagementComponent;
  let fixture: ComponentFixture<OutdatedManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OutdatedManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OutdatedManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
