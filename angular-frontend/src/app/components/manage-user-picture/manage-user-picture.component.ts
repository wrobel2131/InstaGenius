import {Component, computed, inject} from '@angular/core';
import {TranslocoModule} from "@jsverse/transloco";
import {UserFacade} from "../../facades/user.facade";

@Component({
    selector: 'app-manage-user-picture',
    standalone: true,
    imports: [TranslocoModule],
    templateUrl: './manage-user-picture.component.html',
    styleUrl: './manage-user-picture.component.scss'
})
export class ManageUserPictureComponent {
  private userFacade: UserFacade = inject(UserFacade);

    username = computed(() => {
        const user = this.userFacade.currentUser();
        return user ? user.username : 'Anonymous user';
    });
}
