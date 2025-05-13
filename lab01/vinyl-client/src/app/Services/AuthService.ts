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

  public async registration(email: string, login: string, password: string): Promise<any | null> {
    let res = await this.http.post<any>(`${this.baseUrl}api/register`,
      { login: login, password: password, email: email }
    ).toPromise().catch(
      error => { console.error("register error: ", error); return error }
    ) ?? null;

    if (res && res.accessToken) {
      console.log("api register - ok");
      // писать в локалстораж флаг
      this.setSession(res)
    } else {
      console.log("api register - error");
      // чистить локалсторож
      localStorage.removeItem('accessToken');
      localStorage.removeItem('refreshToken');
    }
    return res;
  }

  private setSession(authResult: any) {
    localStorage.setItem('accessToken', authResult.accessToken);
    localStorage.setItem('refreshToken', authResult.refreshToken);

  }

  logout() {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
  }

  public isLoggedIn() {
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
    const expiresAt = JSON.parse(expiration ? expiration : "");
  }
}