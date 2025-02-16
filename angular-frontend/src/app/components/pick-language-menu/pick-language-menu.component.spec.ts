import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PickLanguageMenuComponent } from './pick-language-menu.component';

describe('PickLanguageMenuComponent', () => {
  let component: PickLanguageMenuComponent;
  let fixture: ComponentFixture<PickLanguageMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PickLanguageMenuComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PickLanguageMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
