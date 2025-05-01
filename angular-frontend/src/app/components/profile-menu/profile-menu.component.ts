import { Component, inject } from '@angular/core';
import { CdkMenu, CdkMenuItem, CdkMenuTrigger } from '@angular/cdk/menu';
import { MatDividerModule } from '@angular/material/divider';
import { RouterModule } from '@angular/router';
import {TranslocoModule} from "@jsverse/transloco";
import Keycloak from "keycloak-js";
import {noop} from "rxjs";
import {UserFacade} from "../../facades/user.facade";

@Component({
    selector: 'app-profile-menu',
    standalone: true,
    imports: [
        CdkMenuTrigger,
        CdkMenu,
        CdkMenuItem,
        MatDividerModule,
        RouterModule,
        TranslocoModule,
    ],
    templateUrl: './profile-menu.component.html',
    styleUrl: './profile-menu.component.scss'
})
export class ProfileMenuComponent {
  private userFacade: UserFacade = inject(UserFacade);
  private readonly keycloak: Keycloak = inject(Keycloak);

  get userProfile() {
      return this.userFacade.currentUser();
  }

  get loadingUserProfile() {
      return this.userFacade.loadingUser;
  }


  onLogout() {
      this.userFacade.clearUser();
      this.keycloak.logout({redirectUri: window.location.origin}).then(noop);
  }
}
