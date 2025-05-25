import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GalleryTileComponent } from './gallery-tile.component';

describe('GalleryTileComponent', () => {
  let component: GalleryTileComponent;
  let fixture: ComponentFixture<GalleryTileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GalleryTileComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GalleryTileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
