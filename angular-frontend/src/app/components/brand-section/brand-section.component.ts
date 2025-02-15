import { Component } from '@angular/core';
import { LogoComponent } from '../logo/logo.component';
import { LandingPageFooterComponent } from '../landing-page-footer/landing-page-footer.component';
import { RouterLink } from '@angular/router';
import { TranslocoModule } from '@ngneat/transloco';

@Component({
  selector: 'app-brand-section',
  standalone: true,
  imports: [
    LogoComponent,
    LandingPageFooterComponent,
    RouterLink,
    TranslocoModule,
  ],
  templateUrl: './brand-section.component.html',
  styleUrl: './brand-section.component.scss',
})
export class BrandSectionComponent {
  logoWidth = '300px';
  logoHeight = '300px';
}
