import {
  provideRouter,
  ViewTransitionsFeatureOptions,
  withComponentInputBinding,
  withEnabledBlockingInitialNavigation,
  withViewTransitions,
} from '@angular/router';
import { provideAnimations } from '@angular/platform-browser/animations';

import { routes } from './app.routes';
import { provideKC } from './keycloak.config';
import { includeBearerTokenInterceptor } from 'keycloak-angular';
import { ApplicationConfig } from '@angular/core';
import { provideHttpClient, withInterceptors } from '@angular/common/http';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(
      routes,
      withComponentInputBinding(),
      withViewTransitions({
        skipInitialTransition: false,
        onViewTransitionCreated: () => {
          window.scrollTo({
            top: 0,
            behavior: 'smooth',
          });
        },
      } as ViewTransitionsFeatureOptions)
    ),
    provideHttpClient(withInterceptors([includeBearerTokenInterceptor])),
    provideAnimations(),
    provideKC(),
  ],
};
