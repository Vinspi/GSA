import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditAlertsComponent } from './edit-alerts.component';

describe('EditAlertsComponent', () => {
  let component: EditAlertsComponent;
  let fixture: ComponentFixture<EditAlertsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditAlertsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditAlertsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
