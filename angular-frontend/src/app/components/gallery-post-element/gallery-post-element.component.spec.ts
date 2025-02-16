import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GalleryPostElementComponent } from './gallery-post-element.component';

describe('GalleryPostElementComponent', () => {
  let component: GalleryPostElementComponent;
  let fixture: ComponentFixture<GalleryPostElementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GalleryPostElementComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(GalleryPostElementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
