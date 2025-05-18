import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AuthHeaderComponent } from '../../../shared/components/auth-header/auth-header.component';
import { LucideAngularModule } from 'lucide-angular';
import { AuthNavMobileComponent } from '../../../shared/components/auth-nav-mobile/auth-nav-mobile.component';

@Component({
  selector: 'app-auth-layout',
  standalone: true,
  imports: [
    RouterOutlet,
    AuthHeaderComponent,
    AuthNavMobileComponent,
    LucideAngularModule,
  ],
  templateUrl: './auth-layout.component.html',
  styleUrl: './auth-layout.component.css',
})
export class AuthLayoutComponent {}
