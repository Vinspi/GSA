import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StatsProductsComponent } from './stats-products.component';

describe('StatsProductsComponent', () => {
  let component: StatsProductsComponent;
  let fixture: ComponentFixture<StatsProductsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StatsProductsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatsProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
