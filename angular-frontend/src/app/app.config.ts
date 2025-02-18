import {
  ApplicationConfig,
  importProvidersFrom,
  isDevMode, provideZoneChangeDetection,
} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideAnimations } from '@angular/platform-browser/animations';
import {provideHttpClient, withInterceptors} from '@angular/common/http';
import { TranslocoHttpLoader } from './transloco-loader';

import {
  DEFAULT_LANGUAGE,
  SUPPORTED_LANGUAGES,
} from './models/supported-languages';


import {provideTransloco} from "@jsverse/transloco";
import {provideKC} from "./keycloak.config";
import {includeBearerTokenInterceptor} from "keycloak-angular";

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideAnimations(),
    provideHttpClient(withInterceptors([includeBearerTokenInterceptor])),
    provideKC(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideTransloco({
      config: {
        availableLangs: SUPPORTED_LANGUAGES.map((language) => language.code),
        defaultLang: DEFAULT_LANGUAGE.code,
        reRenderOnLangChange: true,
        prodMode: !isDevMode(),
      },
      loader: TranslocoHttpLoader,
    }),
    importProvidersFrom(
    ),
  ],
};
