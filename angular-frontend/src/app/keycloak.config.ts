import {
    AutoRefreshTokenService,
    createInterceptorCondition,
    provideKeycloak,
    UserActivityService,
    IncludeBearerTokenCondition,
    withAutoRefreshToken, INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG
} from "keycloak-angular";

const interceptorCondition = createInterceptorCondition<IncludeBearerTokenCondition>({
    urlPattern: /^(http:\/\/localhost:8181)(\/.*)?$/i
})

export const KC_REALM_ID = 'instagenius';
export const KC_CLIENT_ID = 'instagenius-frontend';

export const provideKC = () => provideKeycloak({
    config: {
        realm: 'instagenius',
        url: 'http://localhost:8180', //TODO replace with something more abstract
        clientId: 'instagenius-frontend' //TODO chech clientId for frontend
    },
    initOptions: {
        onLoad: 'check-sso',
        // silentCheckSsoRedirectUri: window.location.origin + '/silent-check-sso.html',
        redirectUri: window.location.origin + '/dashboard',
    },
    features: [
        withAutoRefreshToken({
            onInactivityTimeout: 'logout',
            sessionTimeout: 30000
        })
    ],
    providers: [
        AutoRefreshTokenService,
        UserActivityService,
        {
            provide: INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG,
            useValue: [interceptorCondition]
        }
    ]
})