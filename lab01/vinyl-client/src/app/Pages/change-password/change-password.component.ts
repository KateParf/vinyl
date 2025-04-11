import { Component, Inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html'
})
export class ChangePasswordComponent {

  public changeForm!: FormGroup;

  constructor(private formBuilder: FormBuilder, private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    this.changeForm = this.formBuilder.group({
      newPassword: [""]
    })
  }

  changePassword() {
    this.http.post<any>("/api/password_change", this.changeForm.value)
      .subscribe(res => {
        alert('PASSWORD CHANGED SUCCESFUL');
        this.changeForm.reset()
        this.router.navigate(["login"])
      }, err => {
        alert("Something went wrong")
      })
  }

}
