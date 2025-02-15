import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BillingInfoPageComponent } from './billing-info-page.component';

describe('BillingInfoPageComponent', () => {
  let component: BillingInfoPageComponent;
  let fixture: ComponentFixture<BillingInfoPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BillingInfoPageComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(BillingInfoPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
