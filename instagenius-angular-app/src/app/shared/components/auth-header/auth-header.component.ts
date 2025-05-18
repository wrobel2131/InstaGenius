import { Component } from '@angular/core';
import { LogoComponent } from '../logo/logo.component';
import {
  BellIcon,
  ChevronDownIcon,
  CoinsIcon,
  LucideAngularModule,
} from 'lucide-angular';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-auth-header',
  standalone: true,
  imports: [
    LogoComponent,
    LucideAngularModule,
    RouterLink,
    RouterLinkActive,
    CommonModule,
  ],
  templateUrl: './auth-header.component.html',
  styleUrl: './auth-header.component.css',
})
export class AuthHeaderComponent {
  coins = CoinsIcon;
  notifications = BellIcon;
  chevronDown = ChevronDownIcon;
  notificationCount = 8;
  credits = 30;
}
