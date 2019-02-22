import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TriggeredAlertsComponent } from './triggered-alerts.component';

describe('TriggeredAlertsComponent', () => {
  let component: TriggeredAlertsComponent;
  let fixture: ComponentFixture<TriggeredAlertsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TriggeredAlertsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TriggeredAlertsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
