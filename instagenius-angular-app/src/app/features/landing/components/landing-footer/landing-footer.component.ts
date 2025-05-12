import { Component } from '@angular/core';
import { LogoComponent } from '../../../../shared/components/logo/logo.component';
import { IconComponent } from '../../../../shared/components/icon/icon.component';

@Component({
  selector: 'app-landing-footer',
  standalone: true,
  imports: [LogoComponent, IconComponent],
  templateUrl: './landing-footer.component.html',
  styleUrl: './landing-footer.component.css',
})
export class LandingFooterComponent {}
