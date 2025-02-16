import { Routes } from '@angular/router';

export const authenticatedRoutes: Routes = [
  {
    path: '',
    redirectTo: 'generator',
    pathMatch: 'full',
  },
  {
    path: 'generator',
    loadComponent: () =>
      import('./components/generator-page/generator-page.component').then(
        (c) => c.GeneratorPageComponent
      ),
  },
  {
    path: 'gallery',
    loadComponent: () =>
      import('./components/gallery-page/gallery-page.component').then(
        (c) => c.GalleryPageComponent
      ),
  },
  {
    path: 'settings',
    loadComponent: () =>
      import('./components/settings-page/settings-page.component').then(
        (c) => c.SettingPageComponent
      ),
    outlet: 'main',
  },
  {
    path: 'displayed',
    loadComponent: () =>
      import('./components/displayed-post/displayed-post.component').then(
        (c) => c.DisplayedPostComponent
      ),
    outlet: 'main',
  },
  {
    path: 'buy-coins',
    loadComponent: () =>
      import('./components/buy-coins-page/buy-coins-page.component').then(
        (c) => c.BuyCoinsPageComponent
      ),
    outlet: 'main',
  },
];
