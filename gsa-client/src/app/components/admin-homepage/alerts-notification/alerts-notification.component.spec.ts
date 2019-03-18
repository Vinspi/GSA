import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AlertsNotificationComponent } from './alerts-notification.component';

describe('AlertsNotificationComponent', () => {
  let component: AlertsNotificationComponent;
  let fixture: ComponentFixture<AlertsNotificationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AlertsNotificationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AlertsNotificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
