import {Component, inject} from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { LogoComponent } from '../logo/logo.component';
import { RouterLink } from '@angular/router';
import { trigger, transition, style, animate } from '@angular/animations';
import { PickLanguageMenuComponent } from '../pick-language-menu/pick-language-menu.component';
import {TranslocoModule} from "@jsverse/transloco";
import Keycloak from "keycloak-js";

export const fadeInAnimation = trigger('inOutAnimation', [
  transition(':enter', [
    style({ opacity: 0 }),
    animate('200ms ease-out', style({ opacity: 1 })), // Adjust the duration as needed
  ]),
]);

@Component({
    selector: 'app-landing-page-header',
    standalone: true,
    imports: [
        MatButtonModule,
        LogoComponent,
        RouterLink,
        TranslocoModule,
        PickLanguageMenuComponent,
    ],
    templateUrl: './landing-page-header.component.html',
    styleUrl: './landing-page-header.component.scss',
    animations: [fadeInAnimation]
})
export class LandingPageHeaderComponent {
  logoWidth: string = '120px';
  logoHeight: string = '120px';
  isMenuExpanded: boolean = false;
  private readonly keycloak: Keycloak = inject(Keycloak);

  onChangeMobileMenuState(): boolean {
    return (this.isMenuExpanded = !this.isMenuExpanded);
  }

  onLogin(): void {
      console.log("Logging in....")
    this.keycloak.login().then(e => console.log("Logged in"));
  }

  onRegister(): void {
      console.log("Creating new account in....")
      this.keycloak.register().then(e => console.log("Created account in"));
  }




}
