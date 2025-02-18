import { Routes } from '@angular/router';
import { AuthenticatedLayoutComponent } from './components/authenticated-layout/authenticated-layout.component';
import {canActivateBasedOnRole} from "./guards/role-based.guard";
import {canActivateGuest} from "./guards/guest.guard";

export const routes: Routes = [
  {
    path: '',
    canActivate: [canActivateGuest],
    loadComponent: () =>
      import('./components/landing-page/landing-page.component').then(
        (c) => c.LandingPageComponent
      ),
    data: {
      animation: 'down',
    },
  },
  {
    path: 'dashboard',
    component: AuthenticatedLayoutComponent,
    canActivateChild: [canActivateBasedOnRole],
    loadChildren: () =>
      import('./authenticated.routes').then((r) => r.authenticatedRoutes),
    data: {
      animation: 'right',
    },
  },
];
