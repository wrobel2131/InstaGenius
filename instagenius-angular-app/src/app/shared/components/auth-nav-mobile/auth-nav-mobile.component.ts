import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import {
  ImagesIcon,
  LucideAngularModule,
  SparklesIcon,
  UserIcon,
} from 'lucide-angular';

@Component({
  selector: 'app-auth-nav-mobile',
  imports: [RouterLink, RouterLinkActive, LucideAngularModule],
  templateUrl: './auth-nav-mobile.component.html',
  styleUrl: './auth-nav-mobile.component.css',
})
export class AuthNavMobileComponent {
  sparkles = SparklesIcon;
  images = ImagesIcon;
  user = UserIcon;
}
