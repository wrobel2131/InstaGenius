import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostActionBarComponent } from './post-action-bar.component';

describe('PostActionBarComponent', () => {
  let component: PostActionBarComponent;
  let fixture: ComponentFixture<PostActionBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostActionBarComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(PostActionBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
