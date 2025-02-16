import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GeneratedPostComponent } from './generated-post.component';

describe('GeneratedPostComponent', () => {
  let component: GeneratedPostComponent;
  let fixture: ComponentFixture<GeneratedPostComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GeneratedPostComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(GeneratedPostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
