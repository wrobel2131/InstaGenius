import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageUserPictureComponent } from './manage-user-picture.component';

describe('ManageUserPictureComponent', () => {
  let component: ManageUserPictureComponent;
  let fixture: ComponentFixture<ManageUserPictureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManageUserPictureComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ManageUserPictureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
