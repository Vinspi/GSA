import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAliquoteComponent } from './add-aliquote.component';

describe('AddAliquoteComponent', () => {
  let component: AddAliquoteComponent;
  let fixture: ComponentFixture<AddAliquoteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddAliquoteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddAliquoteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
