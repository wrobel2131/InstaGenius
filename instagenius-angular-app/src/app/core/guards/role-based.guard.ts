import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { AuthGuardData, createAuthGuard } from 'keycloak-angular';
import { inject } from '@angular/core';
import { KC_CLIENT_ID } from '../../keycloak.config';

const isAccessAllowed = async (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot,
  authData: AuthGuardData
): Promise<boolean | UrlTree> => {
  const { authenticated, grantedRoles } = authData;
  console.log('route path:', route.routeConfig?.path);
  console.log('isAuthenticated:', authenticated);

  if (!authenticated) {
    const router = inject(Router);
    router.navigate(['/']);
    return false;
  }

  const requiredRoles: string[] = route.data['roles'];
  console.log('required roles:', requiredRoles);

  if (!requiredRoles || requiredRoles.length === 0) {
    console.log('No required roles specified');
    const router = inject(Router);
    router.navigate(['/access-denied']);
    return false;
  }

  const userResourceRoles = grantedRoles.resourceRoles[KC_CLIENT_ID];
  console.log('userResourceRoles:', userResourceRoles);

  const hasRequiredRole = requiredRoles.some((r) =>
    userResourceRoles?.includes(r)
  );
  console.log('hasRequiredRole:', hasRequiredRole);

  if (hasRequiredRole) {
    return true;
  }

  const router = inject(Router);
  router.navigate(['/access-denied']);
  return false;
};

export const canActivateBasedOnRole =
  createAuthGuard<CanActivateFn>(isAccessAllowed);
