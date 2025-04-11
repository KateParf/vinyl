import { Component, Inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {
  public loginForm!: FormGroup;
  
    constructor(private formBuilder: FormBuilder, private http: HttpClient, private router: Router) { }
  
    ngOnInit(): void {
      this.loginForm = this.formBuilder.group({
        login: [""],
        password: [""]
      })
    }
  
    login(){
      this.http.post<any>("/api/login",this.loginForm.value)
      .subscribe(res=>{
        alert('SIGNIN SUCCESFUL');
        this.loginForm.reset()
        this.router.navigate(["login"])
      },err=>{
        alert("Something went wrong")
      })
    }

}
