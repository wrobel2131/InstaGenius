import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GithubLoginButtonComponent } from './github-login-button.component';

describe('GithubLoginButtonComponent', () => {
  let component: GithubLoginButtonComponent;
  let fixture: ComponentFixture<GithubLoginButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GithubLoginButtonComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(GithubLoginButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
