import { Component, HostBinding, HostListener, OnInit } from '@angular/core';
import { LandingPageHeaderComponent } from '../landing-page-header/landing-page-header.component';
import { LandingPageContentComponent } from '../landing-page-content/landing-page-content.component';
import { LandingPageFooterComponent } from '../landing-page-footer/landing-page-footer.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-landing-page',
  standalone: true,
  imports: [
    LandingPageHeaderComponent,
    LandingPageContentComponent,
    LandingPageFooterComponent,
    CommonModule,
  ],
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.scss',
})
export class LandingPageComponent {}
