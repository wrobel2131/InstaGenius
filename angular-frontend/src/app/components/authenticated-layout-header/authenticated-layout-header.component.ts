import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { BuyCoinsButtonComponent } from '../buy-coins-button/buy-coins-button.component';
import { ProfileMenuComponent } from '../profile-menu/profile-menu.component';

@Component({
  selector: 'app-authenticated-layout-header',
  standalone: true,
  imports: [RouterLink, BuyCoinsButtonComponent, ProfileMenuComponent],
  templateUrl: './authenticated-layout-header.component.html',
  styleUrl: './authenticated-layout-header.component.scss',
})
export class AuthenticatedLayoutHeaderComponent {}
