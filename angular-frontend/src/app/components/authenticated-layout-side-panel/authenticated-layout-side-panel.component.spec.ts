import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthenticatedLayoutSidePanelComponent } from './authenticated-layout-side-panel.component';

describe('AuthenticatedLayoutSidePanelComponent', () => {
  let component: AuthenticatedLayoutSidePanelComponent;
  let fixture: ComponentFixture<AuthenticatedLayoutSidePanelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuthenticatedLayoutSidePanelComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(AuthenticatedLayoutSidePanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
