import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuyCoinsPageComponent } from './buy-coins-page.component';

describe('BuyCoinsPageComponent', () => {
  let component: BuyCoinsPageComponent;
  let fixture: ComponentFixture<BuyCoinsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BuyCoinsPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BuyCoinsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
