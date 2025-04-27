import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {AuthGuardData, createAuthGuard} from "keycloak-angular";
import {KC_CLIENT_ID} from "../keycloak.config";
import {inject} from "@angular/core";

const isAccessAllowed = async(route: ActivatedRouteSnapshot, state: RouterStateSnapshot, authData: AuthGuardData):
    Promise<boolean | UrlTree> => {
    const {authenticated, grantedRoles} = authData;

    console.log("isAuthenticated: {}", authenticated);
    if(!authenticated) {
        return true;
    }
    const router = inject(Router);
    return router.navigate(['/dashboard']);
};

export const canActivateGuest = createAuthGuard<CanActivateFn>(isAccessAllowed);