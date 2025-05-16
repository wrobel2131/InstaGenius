import { Component, inject } from '@angular/core';
import { LogoComponent } from '../logo/logo.component';
import { RouterLink } from '@angular/router';
import Keycloak from 'keycloak-js';

@Component({
  selector: 'app-landing-header',
  standalone: true,
  imports: [LogoComponent, RouterLink],
  templateUrl: './landing-header.component.html',
  styleUrl: './landing-header.component.css',
})
export class LandingHeaderComponent {
  isMenuOpen = false;
  private readonly keycloak = inject(Keycloak);

  onLogin(): void {
    console.log('Logging in....');
    this.keycloak.login().then(() => console.log('Logged in'));
  }

  onRegister(): void {
    console.log('Creating new account in....');
    this.keycloak.register().then(() => console.log('Created account in'));
  }

  onMenuToogle() {
    this.isMenuOpen = !this.isMenuOpen;
  }
}
