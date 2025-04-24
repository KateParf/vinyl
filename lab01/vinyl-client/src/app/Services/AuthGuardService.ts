import { ActivatedRouteSnapshot, createUrlTreeFromSnapshot, RouterStateSnapshot } from "@angular/router";
import { APIService } from "./api";
import { inject } from "@angular/core";

export const canActivate = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
    console.log("canActivate");

    const apiService = inject(APIService);
    if (apiService.isAuth()) return true;

    return createUrlTreeFromSnapshot(route, ['/login'], { returnUrl: state.url });
}