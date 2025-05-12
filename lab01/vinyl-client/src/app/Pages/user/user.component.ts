import { Component, Inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { APIService } from '../../Services/api';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html'
})
export class UserComponent {

  public changeForm!: FormGroup;
  public userName: string = "";
  public userEmail: string = "";

  public isError: boolean | null = null;

  constructor(private formBuilder: FormBuilder, private http: HttpClient, private router: Router, private apiService: APIService) { }

  async ngOnInit() {
    this.changeForm = new FormGroup({
      oldPassword: new FormControl('', Validators.required),
      newPassword: new FormControl('', Validators.required)
    });

    var user = await this.apiService.getUserInfo();
    if (user) {
      this.userEmail = user.email;
      this.userName = user.login;
    }

    this.isError = null;
  }

  async changePassword() {
    let res = await this.apiService.changePassword(this.changeForm.value["oldPassword"], this.changeForm.value["newPassword"]);
    if (res.status == 0) {
      this.isError = false;
      console.log("change passw ok");
    } else {
      this.isError = true;
      console.log("change passw NE ok");
    }
  }

}
