import { HttpClient } from "@angular/common/http";
import { Inject, Injectable } from "@angular/core";

@Injectable({ providedIn: 'root' })
export class AuthService {
    private baseUrl: string = "";

    constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string) {
        this.baseUrl = baseUrl;
      }
    
    public async login(login: string, password: string): Promise<any | null> {
        let res = await this.http.post<any>(`${this.baseUrl}api/login`,
          { login: login, password: password }
        ).toPromise().catch(
          error => console.error("login error: ", error)
        ) ?? null;
    
        // testdata
        ////!!if (login == "katya") {
        if (res && res.accessToken) {
          console.log("api login - ok");
          // писать в локалстораж флаг
          this.setSession(res)
        } else {
          console.log("api login - error");
          // чистить локалсторож
          localStorage.removeItem('accessToken');
          localStorage.removeItem('refreshToken');
        }
        return res;
    }
    
          
    private setSession(authResult: any) {
        localStorage.setItem('accessToken', authResult.accessToken);
        localStorage.setItem('refreshToken', authResult.refreshToken);        

        //const expiresAt = moment().add(authResult.expiresIn,'second');
        //localStorage.setItem("expires_at", JSON.stringify(expiresAt.valueOf()) );
    }          

    logout() {
        //?? надо ли вызывать что то на сервере ??
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
    }

    public isLoggedIn() {
        //return moment().isBefore(this.getExpiration());

        // анализировать флаг из сторожа
        let flag = localStorage.getItem('accessToken');
        console.log("isauth = ", flag);
        if (flag) 
            return true;
        else 
            return false;
    }

    isLoggedOut() {
        return !this.isLoggedIn();
    }

    getExpiration() {
        const expiration = localStorage.getItem("expires_at");
        const expiresAt = JSON.parse(expiration?expiration:"");
        //return moment(expiresAt);
    }    
}