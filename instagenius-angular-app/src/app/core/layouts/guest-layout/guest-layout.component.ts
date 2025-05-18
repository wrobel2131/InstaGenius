import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LandingFooterComponent } from '../../../shared/components/landing-footer/landing-footer.component';
import { LandingHeaderComponent } from '../../../shared/components/landing-header/landing-header.component';
import { ArrowUpIcon, LucideAngularModule } from 'lucide-angular';

@Component({
  selector: 'app-guest-layout',
  standalone: true,
  imports: [
    RouterOutlet,
    LandingFooterComponent,
    LandingHeaderComponent,
    LucideAngularModule,
  ],
  templateUrl: './guest-layout.component.html',
  styleUrl: './guest-layout.component.css',
})
export class GuestLayoutComponent {
  readonly arrowUp = ArrowUpIcon;

  scrollToTop(): void {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
}
