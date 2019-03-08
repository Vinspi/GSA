import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StatsProvidersComponent } from './stats-providers.component';

describe('StatsProvidersComponent', () => {
  let component: StatsProvidersComponent;
  let fixture: ComponentFixture<StatsProvidersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StatsProvidersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatsProvidersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
