import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuyCoinsButtonComponent } from './buy-coins-button.component';

describe('BuyCoinsButtonComponent', () => {
  let component: BuyCoinsButtonComponent;
  let fixture: ComponentFixture<BuyCoinsButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BuyCoinsButtonComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(BuyCoinsButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
