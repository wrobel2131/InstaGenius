import { Component } from '@angular/core';
import { TranslocoModule } from '@ngneat/transloco';

@Component({
  selector: 'app-landing-page-footer',
  standalone: true,
  imports: [TranslocoModule],
  templateUrl: './landing-page-footer.component.html',
  styleUrl: './landing-page-footer.component.scss',
})
export class LandingPageFooterComponent {}
