import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { TranslocoModule } from '@ngneat/transloco';
import { UserDataService } from '../../services/user-data.service';

@Component({
  selector: 'app-buy-coins-button',
  standalone: true,
  imports: [TranslocoModule, RouterLink],
  templateUrl: './buy-coins-button.component.html',
  styleUrl: './buy-coins-button.component.scss',
})
export class BuyCoinsButtonComponent {
  private userDataService: UserDataService = inject(UserDataService);

  user = this.userDataService.user;
}
