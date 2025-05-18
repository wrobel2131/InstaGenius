import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthNavMobileComponent } from './auth-nav-mobile.component';

describe('AuthNavMobileComponent', () => {
  let component: AuthNavMobileComponent;
  let fixture: ComponentFixture<AuthNavMobileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuthNavMobileComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuthNavMobileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
