import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { LogoComponent } from '../logo/logo.component';
import { RouterLink } from '@angular/router';
import { trigger, transition, style, animate } from '@angular/animations';
import { TranslocoModule } from '@ngneat/transloco';
import { PickLanguageMenuComponent } from '../pick-language-menu/pick-language-menu.component';
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
  animations: [fadeInAnimation],
})
export class LandingPageHeaderComponent {
  logoWidth = '120px';
  logoHeight = '120px';
  isMenuExpanded = false;

  onChangeMobileMenuState() {
    return (this.isMenuExpanded = !this.isMenuExpanded);
  }
}
