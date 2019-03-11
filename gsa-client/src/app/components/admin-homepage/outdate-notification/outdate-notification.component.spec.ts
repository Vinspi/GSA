import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OutdateNotificationComponent } from './outdate-notification.component';

describe('OutdateNotificationComponent', () => {
  let component: OutdateNotificationComponent;
  let fixture: ComponentFixture<OutdateNotificationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OutdateNotificationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OutdateNotificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
