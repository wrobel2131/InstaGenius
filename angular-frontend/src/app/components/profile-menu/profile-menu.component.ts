import { Component, inject } from '@angular/core';
import { CdkMenu, CdkMenuItem, CdkMenuTrigger } from '@angular/cdk/menu';
import { MatDividerModule } from '@angular/material/divider';
import { RouterModule } from '@angular/router';
import { TranslocoModule } from '@ngneat/transloco';
import { UserDataService } from '../../services/user-data.service';

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
  styleUrl: './profile-menu.component.scss',
})
export class ProfileMenuComponent {
  private userDataService: UserDataService = inject(UserDataService);

  user = this.userDataService.user;

  onLogout() {
    console.log('component logout');
    this.userDataService.logout();
  }
}
