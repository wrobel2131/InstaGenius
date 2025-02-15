import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageUserDataComponent } from './manage-user-data.component';

describe('ManageUserDataComponent', () => {
  let component: ManageUserDataComponent;
  let fixture: ComponentFixture<ManageUserDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManageUserDataComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ManageUserDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
