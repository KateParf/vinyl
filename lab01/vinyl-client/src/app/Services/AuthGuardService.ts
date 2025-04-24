import { ActivatedRouteSnapshot, createUrlTreeFromSnapshot, RouterStateSnapshot } from "@angular/router";
import { inject } from "@angular/core";
import {AuthService} from "./AuthService";

export const canActivate = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
    console.log("canActivate");

    const authService = inject(AuthService);
    if (authService.isLoggedIn()) return true;

    return createUrlTreeFromSnapshot(route, ['/login'], { returnUrl: state.url });
}