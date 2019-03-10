/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { EditReportComponent } from './edit-report.component';

describe('EditReportComponent', () => {
  let component: EditReportComponent;
  let fixture: ComponentFixture<EditReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
