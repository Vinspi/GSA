/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { ShowReportComponent } from './show-report.component';

describe('showReportComponent', () => {
  let component: ShowReportComponent;
  let fixture: ComponentFixture<ShowReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
