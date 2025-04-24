import { Component, Inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { APIService } from '../../Services/api';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html'
})
export class UserComponent {

  public changeForm!: FormGroup;
  public userName: string = "";
  public userEmail: string = "";

  constructor(private formBuilder: FormBuilder, private http: HttpClient, private router: Router, private apiService: APIService) { }

  ngOnInit(): void {
    this.changeForm = this.formBuilder.group({
      oldPassword: [""],
      newPassword: [""]
    })
    var res = this.apiService.getUserInfo();
    this.userEmail = res.email;
    this.userName = res.name;
  }

  changePassword() {
    let res = this.apiService.login(this.changeForm.value["oldPassword"], this.changeForm.value["newPassword"]);
  }

}
