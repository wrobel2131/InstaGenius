import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {AuthGuardData, createAuthGuard} from "keycloak-angular";
import {KC_CLIENT_ID} from "../keycloak.config";
import {inject} from "@angular/core";

const isAccessAllowed = async(route: ActivatedRouteSnapshot, state: RouterStateSnapshot, authData: AuthGuardData):
    Promise<boolean | UrlTree> => {
    const {authenticated, grantedRoles} = authData;
    console.log("route: " + route);
    console.log("isAuthenticated: ", authenticated);

    const requiredRoles : string[] = route.data['roles'];
    console.log("roles: ", requiredRoles);

    if(requiredRoles == null || requiredRoles.length == 0) {
        return false;
    }

    const userResourceRoles = grantedRoles.resourceRoles[KC_CLIENT_ID];
    console.log("userResourceRoles: ", userResourceRoles);

    const hasRequiredRole = requiredRoles.some(r => userResourceRoles?.includes(r));

    console.log("hasRequiredRole: ", hasRequiredRole);
    if(authenticated && hasRequiredRole) {
        return true;
    }

    const router = inject(Router);
    return router.parseUrl('');
};

export const canActivateBasedOnRole = createAuthGuard<CanActivateFn>(isAccessAllowed);