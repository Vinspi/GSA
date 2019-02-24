import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AliquotManagementComponent } from './aliquot-management.component';

describe('AliquotManagementComponent', () => {
  let component: AliquotManagementComponent;
  let fixture: ComponentFixture<AliquotManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AliquotManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AliquotManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
