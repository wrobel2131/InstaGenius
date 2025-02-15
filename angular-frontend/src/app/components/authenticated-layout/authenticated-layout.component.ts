import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AuthenticatedLayoutSidePanelComponent } from '../authenticated-layout-side-panel/authenticated-layout-side-panel.component';
import { AuthenticatedLayoutHeaderComponent } from '../authenticated-layout-header/authenticated-layout-header.component';
import { GalleryPostElementComponent } from '../gallery-post-element/gallery-post-element.component';

@Component({
  selector: 'app-authenticated-layout',
  standalone: true,
  imports: [
    AuthenticatedLayoutSidePanelComponent,
    AuthenticatedLayoutHeaderComponent,
    GalleryPostElementComponent,
    RouterOutlet,
  ],
  templateUrl: './authenticated-layout.component.html',
  styleUrl: './authenticated-layout.component.scss',
})
export class AuthenticatedLayoutComponent {}
