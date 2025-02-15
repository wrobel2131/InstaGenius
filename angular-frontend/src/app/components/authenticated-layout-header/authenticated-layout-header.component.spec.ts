import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthenticatedLayoutHeaderComponent } from './authenticated-layout-header.component';

describe('AuthenticatedLayoutHeaderComponent', () => {
  let component: AuthenticatedLayoutHeaderComponent;
  let fixture: ComponentFixture<AuthenticatedLayoutHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuthenticatedLayoutHeaderComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(AuthenticatedLayoutHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
