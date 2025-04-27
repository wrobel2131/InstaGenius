import { Component, inject } from '@angular/core';
import { CdkMenu, CdkMenuItem, CdkMenuTrigger } from '@angular/cdk/menu';
import { MatDividerModule } from '@angular/material/divider';
import { RouterModule } from '@angular/router';
import { UserDataService } from '../../services/user-data.service';
import {TranslocoModule} from "@jsverse/transloco";
import Keycloak from "keycloak-js";
import {noop} from "rxjs";

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
  private userDataService: UserDataService = inject(UserDataService);
  private readonly keycloak: Keycloak = inject(Keycloak);

  user = this.userDataService.user;

  onLogout() {
    this.keycloak.logout({redirectUri: window.location.origin}).then(noop);

  }
}
