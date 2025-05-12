import { Component } from '@angular/core';
import { LogoComponent } from '../../../../shared/components/logo/logo.component';

@Component({
  selector: 'app-landing-header',
  standalone: true,
  imports: [LogoComponent],
  templateUrl: './landing-header.component.html',
  styleUrl: './landing-header.component.css',
})
export class LandingHeaderComponent {
  isMenuOpen = false;

  onLogin() {
    console.log('login'); //TODO login from keycloak
  }

  onRegister() {
    console.log('register'); //TODO login from keycloak
  }

  onMenuToogle() {
    this.isMenuOpen = !this.isMenuOpen;
  }
}
