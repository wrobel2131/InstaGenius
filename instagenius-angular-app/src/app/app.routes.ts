import { RouteConfigLoadEnd, Routes } from '@angular/router';
import { GuestLayoutComponent } from './core/layouts/guest-layout/guest-layout.component';
import { ROUTES } from './routes';
import { AuthLayoutComponent } from './core/layouts/auth-layout/auth-layout.component';
import { canActivateGuest } from './core/guards/guest.guard';
import { canActivateBasedOnRole } from './core/guards/role-based.guard';

export const routes: Routes = [
  {
    path: '',
    component: GuestLayoutComponent,
    canActivate: [canActivateGuest],
    children: [
      {
        path: '',
        loadComponent: () =>
          import('./features/home/pages/home-page/home-page.component').then(
            (c) => c.HomePageComponent
          ),
      },
      {
        path: ROUTES.CONTACT,
        loadComponent: () =>
          import(
            './features/contact/pages/contact-page/contact-page.component'
          ).then((c) => c.ContactPageComponent),
      },
    ],
  },
  {
    path: '',
    component: AuthLayoutComponent,
    canActivate: [canActivateBasedOnRole],
    data: {
      roles: ['USER', 'ADMIN'],
    },
    children: [
      {
        path: ROUTES.GENERATOR,
        data: {
          roles: ['USER', 'ADMIN'],
        },
        loadComponent: () =>
          import(
            './features/generator/pages/generator-page/generator-page.component'
          ).then((c) => c.GeneratorPageComponent),
      },
      {
        path: ROUTES.GALLERY,
        data: {
          roles: ['USER', 'ADMIN'],
        },
        loadComponent: () =>
          import(
            './features/gallery/pages/gallery-page/gallery-page.component'
          ).then((c) => c.GalleryPageComponent),
      },
      {
        path: ROUTES.PROFILE,
        data: {
          roles: ['USER', 'ADMIN'],
        },
        loadComponent: () =>
          import(
            './features/profile/pages/profile-page/profile-page.component'
          ).then((c) => c.ProfilePageComponent),
      },
      {
        path: ROUTES.COINS,
        data: {
          roles: ['USER', 'ADMIN'],
        },
        loadComponent: () =>
          import(
            './features/coins/pages/buy-coins-page/buy-coins-page.component'
          ).then((c) => c.BuyCoinsPageComponent),
      },
    ],
  },
];
