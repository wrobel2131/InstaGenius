import { Component, inject } from '@angular/core';
import { LogoComponent } from '../logo/logo.component';
import {
  FacebookIcon,
  InstagramIcon,
  LinkedinIcon,
  LucideAngularModule,
} from 'lucide-angular';
import { RouterLink } from '@angular/router';
import Keycloak from 'keycloak-js';

@Component({
  selector: 'app-landing-footer',
  standalone: true,
  imports: [LogoComponent, LucideAngularModule, RouterLink],
  templateUrl: './landing-footer.component.html',
  styleUrl: './landing-footer.component.css',
})
export class LandingFooterComponent {
  readonly instagram = InstagramIcon;
  readonly linkedin = LinkedinIcon;
  readonly facebook = FacebookIcon;
  private readonly keycloak: Keycloak = inject(Keycloak);

  onLogin(): void {
    console.log('Logging in....');
    this.keycloak.login().then((e) => console.log('Logged in'));
  }

  onRegister(): void {
    console.log('Creating new account in....');
    this.keycloak.register().then((e) => console.log('Created account in'));
  }
}
