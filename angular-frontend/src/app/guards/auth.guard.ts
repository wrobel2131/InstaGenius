import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { UserDataService } from '../services/user-data.service';
import { AuthService } from '../services/auth.service';

export const AuthGuard: CanActivateFn = (route, state) => {
  const userDataService: UserDataService = inject(UserDataService);
  const router: Router = inject(Router);
  const authService: AuthService = inject(AuthService);

  if (!userDataService.isAuthenticated()) {
    authService.clearLocalStorage();
    return router.createUrlTree(['/signin']);
  }
  // normally userId need to be set differently
  userDataService.setUserId(Number(localStorage.getItem('userId')));
  return true;
};
