import { Component, OnInit } from '@angular/core';
import {
  ChildrenOutletContexts,
  Route,
  RouterLink,
  RouterLinkActive,
  RouterOutlet,
} from '@angular/router';
import { MatTabsModule } from '@angular/material/tabs';
import { LogoComponent } from '../logo/logo.component';
import { CommonModule } from '@angular/common';
import { routes } from '../../app.routes';
import { TranslocoModule } from '@ngneat/transloco';
import { PickLanguageMenuComponent } from '../pick-language-menu/pick-language-menu.component';
import { authenticatedRoutes } from '../../authenticated.routes';

@Component({
  selector: 'app-authenticated-layout-side-panel',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    RouterOutlet,
    MatTabsModule,
    LogoComponent,
    RouterLinkActive,
    PickLanguageMenuComponent,
    TranslocoModule,
  ],
  templateUrl: './authenticated-layout-side-panel.component.html',
  styleUrl: './authenticated-layout-side-panel.component.scss',
})
export class AuthenticatedLayoutSidePanelComponent implements OnInit {
  links!: Route[];

  ngOnInit(): void {
    this.links = this.getDashboardLinks();
  }

  private getDashboardLinks(): Route[] {
    return authenticatedRoutes.filter(
      (route) =>
        route.path !== '' && ['gallery', 'generator'].includes(route.path!)
    );
  }
}
