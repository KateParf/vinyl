import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable()
export class AuthInterceptor implements HttpInterceptor, AuthInterceptor {

    intercept(req: HttpRequest<any>,
              next: HttpHandler): Observable<HttpEvent<any>> {

        //console.log("intercept");
        const accessToken = localStorage.getItem("accessToken");

        if (accessToken) {
            //console.log("interceptor uses token: ", accessToken);
            const cloned = req.clone({
                headers: req.headers.set("Authorization", "Bearer " + accessToken)
            });

            return next.handle(cloned);
        }
        else {
            return next.handle(req);
        }
    }
}