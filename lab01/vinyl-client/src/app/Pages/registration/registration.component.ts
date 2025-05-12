import { Component, Inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { APIService } from '../../Services/api';
import { AuthService } from '../../Services/AuthService';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html'
})
export class UserRegistrationComponent {
  public authForm!: FormGroup;
  public isError: boolean = false;

  constructor(private formBuilder: FormBuilder, private http: HttpClient, private router: Router,
    private apiService: APIService, private authService: AuthService) {
  }

  ngOnInit(): void {
    this.authForm = new FormGroup({
      login: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required)
    });
  }

  public async registration() {
    let res = await this.authService.registration(this.authForm.value["email"], this.authForm.value["login"], this.authForm.value["password"]);
    if (res && (res.error == "" || res.error == null)) {
      console.log("register ok");
      console.log(this.authService.isLoggedIn());
      this.router.navigate(["/user"]);
    } else {
      // fail
      console.log("register fail", res);
      console.log(this.authService.isLoggedIn());
      this.authForm.reset();
      // вывести ошибку !!!
      this.isError = true;
    }
  }

}
