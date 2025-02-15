import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MobileFooterDarkComponent } from './mobile-footer-dark.component';

describe('MobileFooterDarkComponent', () => {
  let component: MobileFooterDarkComponent;
  let fixture: ComponentFixture<MobileFooterDarkComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MobileFooterDarkComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(MobileFooterDarkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
