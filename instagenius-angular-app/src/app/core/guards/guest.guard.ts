import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { AuthGuardData, createAuthGuard } from 'keycloak-angular';
import { inject } from '@angular/core';
import { ROUTES } from '../../routes';

const isAccessAllowed = async (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot,
  authData: AuthGuardData
): Promise<boolean | UrlTree> => {
  const { authenticated } = authData;
  console.log('isAuthenticated:', authenticated);

  if (!authenticated) {
    console.log('returning true, not authenticated');
    return true;
  }

  console.log('authenticated');
  const router = inject(Router);
  router.navigate([ROUTES.GENERATOR]);
  return false;
};

export const canActivateGuest = createAuthGuard<CanActivateFn>(isAccessAllowed);
