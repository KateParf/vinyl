import { Component, Inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../Services/AuthService';
import { APIService } from '../../Services/api';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {
  public loginForm!: FormGroup;

  private returnUrl: any;
  public isError: boolean = false;
  public errorMess: string = "";

  constructor(private formBuilder: FormBuilder, private http: HttpClient,
    private route: ActivatedRoute, private router: Router, private authService: AuthService, private apiService: APIService) { }

  ngOnInit(): void {
    if (this.authService.isLoggedIn()) {
      this.router.navigate(["/user"]);
    }

    this.loginForm = new FormGroup({
      login: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });

    this.route.queryParams.subscribe(params => {
      if (params['returnUrl']) {
        this.returnUrl = params['returnUrl'];
      }
      else {
        this.returnUrl = "/user";
      }
    });
  }

  async login() {
    console.log("login ...", this.loginForm.value.login, this.loginForm.value.password);
    let res = await this.authService.login(this.loginForm.value["login"], this.loginForm.value["password"]);
    if (res && (res.error == "" || res.error == null)) {
      // login ok - redir
      console.log("login ok");
      console.log(this.authService.isLoggedIn());

      // получаем ид глобальных пластинок
      await this.apiService.getUserRecordIds();

      this.router.navigate([this.returnUrl]); // навигировать куда шли
    } else {
      // login fail
      console.log("login fail", res);
      this.errorMess = res.error;
      console.log(this.authService.isLoggedIn());
      this.loginForm.reset();
      this.isError = true;
    }
  }

}
