import { Component, Inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { APIService } from '../../Services/api';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {
  public loginForm!: FormGroup;

  private returnUrl: any;
  public isError: boolean = false;

  constructor(private formBuilder: FormBuilder, private http: HttpClient,
    private route: ActivatedRoute, private router: Router, private apiService: APIService) { }

  ngOnInit(): void {
    if (this.apiService.isAuth()){
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

  login() {
    console.log("login ...", this.loginForm.value.login, this.loginForm.value.password);
    let res = this.apiService.login(this.loginForm.value["login"], this.loginForm.value["password"]);
    if (!res) {
      // login ok - redir
      console.log("login ok");
      console.log(this.apiService.isAuth());
      this.router.navigate([this.returnUrl]); //!! TODO навигировать куда шли
    } else {
      // login fail
      console.log("login fail", res);
      console.log(this.apiService.isAuth());
      this.loginForm.reset();
      // вывести ошибку !!!
      this.isError = true;
    }
  }

}
