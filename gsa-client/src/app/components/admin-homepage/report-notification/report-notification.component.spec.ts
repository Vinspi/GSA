import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportNotificationComponent } from './report-notification.component';

describe('ReportNotificationComponent', () => {
  let component: ReportNotificationComponent;
  let fixture: ComponentFixture<ReportNotificationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportNotificationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportNotificationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
