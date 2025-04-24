import { Component, Inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { APIService } from '../../Services/api';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html'
})
export class UserRegistrationComponent {
  public authForm!: FormGroup;
  public isError: boolean = false;

  constructor(private formBuilder: FormBuilder, private http: HttpClient, private router: Router, private apiService: APIService) { }

  ngOnInit(): void {
    this.authForm = this.formBuilder.group({
      email: [""],
      login: [""],
      password: [""]
    })
  }

  registration() {
    let res = this.apiService.registration(this.authForm.value["email"], this.authForm.value["login"], this.authForm.value["password"]);
    if (!res) {
      console.log("register ok");
      console.log(this.apiService.isAuth());
      this.router.navigate(["/user"]);
    } else {
      // fail
      console.log("register fail", res);
      console.log(this.apiService.isAuth());
      this.authForm.reset();
      // вывести ошибку !!!
      this.isError = true;
    }
  }

}
