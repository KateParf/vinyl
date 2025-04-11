import { Component, Inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-authorization',
  templateUrl: './authorization.component.html'
})
export class AuthorizationComponent {
  public authForm!: FormGroup;

  constructor(private formBuilder: FormBuilder, private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    this.authForm = this.formBuilder.group({
      email: [""],
      login: [""],
      password: [""]
    })
  }

  auth(){
    this.http.post<any>("/api/register",this.authForm.value)
    .subscribe(res=>{
      alert('SIGNIN SUCCESFUL');
      this.authForm.reset()
      this.router.navigate(["auth"])
    },err=>{
      alert("Something went wrong")
    })
  }

}
