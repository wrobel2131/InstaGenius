import { RouteConfigLoadEnd, Routes } from '@angular/router';
import { GuestLayoutComponent } from './core/layouts/guest-layout/guest-layout.component';
import { GuestGuard } from './core/guards/guest.guard';
import { ROUTES } from './routes';
import { AuthLayoutComponent } from './core/layouts/auth-layout/auth-layout.component';
import { AuthGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    component: GuestLayoutComponent,
    canActivate: [GuestGuard],
    children: [
      {
        path: '',
        loadComponent: () =>
          import(
            './features/landing/pages/landing-page/landing-page.component'
          ).then((c) => c.LandingPageComponent),
      },
    ],
  },
  {
    path: '',
    component: AuthLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: ROUTES.GENERATOR,
        loadComponent: () =>
          import(
            './features/generator/pages/generator-page/generator-page.component'
          ).then((c) => c.GeneratorPageComponent),
      },
      {
        path: ROUTES.GALLERY,
        loadComponent: () =>
          import(
            './features/gallery/pages/gallery-page/gallery-page.component'
          ).then((c) => c.GalleryPageComponent),
      },
      {
        path: ROUTES.PROFILE,
        loadComponent: () =>
          import(
            './features/profile/pages/profile-page/profile-page.component'
          ).then((c) => c.ProfilePageComponent),
      },
      {
        path: ROUTES.COINS,
        loadComponent: () =>
          import(
            './features/coins/pages/buy-coins-page/buy-coins-page.component'
          ).then((c) => c.BuyCoinsPageComponent),
      },
    ],
  },
];
