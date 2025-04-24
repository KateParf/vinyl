import { Component, Inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import {AuthService} from '../../Services/AuthService';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html'
})
export class LogoutComponent {
  public loginForm!: FormGroup;
  
    constructor(private formBuilder: FormBuilder, private http: HttpClient, private router: Router,  private authService: AuthService) { }
  
    async ngOnInit() {
      await this.authService.logout();
      this.router.navigate(["/"]);
    }
  

}
