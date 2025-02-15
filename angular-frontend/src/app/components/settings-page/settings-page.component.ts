import { Component } from '@angular/core';
import { ManageUserDataComponent } from '../manage-user-data/manage-user-data.component';
import { ManageUserPictureComponent } from '../manage-user-picture/manage-user-picture.component';

@Component({
  selector: 'app-setting-page',
  standalone: true,
  imports: [ManageUserDataComponent, ManageUserPictureComponent],
  templateUrl: './settings-page.component.html',
  styleUrl: './settings-page.component.scss',
})
export class SettingPageComponent {}
