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
    // data: {
    //   animation: 'down',
    // },
  },
  // {
  //   path: 'signin',
  //   loadComponent: () =>
  //     import('./components/login-page/login-page.component').then(
  //       (c) => c.LoginPageComponent
  //     ),
  //   data: {
  //     animation: 'left',
  //   },
  // },
  // {
  //   path: 'signup',
  //   loadComponent: () =>
  //     import('./components/register-page/register-page.component').then(
  //       (c) => c.RegisterPageComponent
  //     ),
  //   data: {
  //     animation: 'right',
  //   },
  // },
  {
    path: 'dashboard',
    component: AuthenticatedLayoutComponent,
    canActivateChild: [canActivateBasedOnRole],
    loadChildren: () =>
      import('./authenticated.routes').then((r) => r.authenticatedRoutes),
    // data: {
    //   animation: 'right',
    // },
  },
];
