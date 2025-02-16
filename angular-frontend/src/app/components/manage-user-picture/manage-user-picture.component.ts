import { Component, inject } from '@angular/core';
import { UserDataService } from '../../services/user-data.service';
import {TranslocoModule} from "@jsverse/transloco";

@Component({
    selector: 'app-manage-user-picture',
    standalone: true,
    imports: [TranslocoModule],
    templateUrl: './manage-user-picture.component.html',
    styleUrl: './manage-user-picture.component.scss'
})
export class ManageUserPictureComponent {
  private userDataService: UserDataService = inject(UserDataService);

  user = this.userDataService.user;
}
