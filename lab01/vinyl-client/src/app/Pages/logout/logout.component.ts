import { Component, Inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { APIService } from '../../Services/api';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html'
})
export class LogoutComponent {
  public loginForm!: FormGroup;
  
    constructor(private formBuilder: FormBuilder, private http: HttpClient, private router: Router,  private apiService: APIService) { }
  
    ngOnInit(): void {
      this.apiService.logout();
      this.router.navigate(["/"]);
    }
  

}
