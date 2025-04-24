import { Component, Inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import {AuthService} from '../../Services/AuthService';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {
  public loginForm!: FormGroup;

  private returnUrl: any;
  public isError: boolean = false;

  constructor(private formBuilder: FormBuilder, private http: HttpClient,
    private route: ActivatedRoute, private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
    if (this.authService.isLoggedIn()){
      this.router.navigate(["/user"]);
    }

    this.loginForm = this.formBuilder.group({
      login: [""],
      password: [""]
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
      this.router.navigate([this.returnUrl]); //!! TODO навигировать куда шли
    } else {
      // login fail
      console.log("login fail", res);
      console.log(this.authService.isLoggedIn());
      this.loginForm.reset();
      // вывести ошибку !!!
      this.isError = true;
    }
  }

}
