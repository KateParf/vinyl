import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { BsDropdownModule } from 'ngx-bootstrap/dropdown';

import { AppComponent } from './app.component';
import { NavMenuComponent } from './nav-menu/nav-menu.component';

import { PerformerComponent } from './Pages/performer/performer.component';
import { GroupComponent } from './Pages/group/group.component';
import { PerformersComponent } from './Pages/performers/performers.component';
import { RecordComponent } from './Pages/record/record.component';
import { RecordsComponent } from './Pages/records/records.component';
import { UserRecordComponent } from './Pages/user-record/user-record.component';
import { UserRecordsComponent } from './Pages/user-records/user-records.component';
import { HomeComponent } from './Pages/home/home.component';
import { UserRegistrationComponent } from './Pages/registration/registration.component';
import { LoginComponent } from './Pages/login/login.component';
import { UserComponent } from './Pages/user/user.component';
import { APIService } from './Services/api';
import { AuthService } from './Services/AuthService';
import { canActivate } from './Services/AuthGuardService';
import { LogoutComponent } from './Pages/logout/logout.component';

@NgModule({
  declarations: [
    AppComponent,
    NavMenuComponent,
    HomeComponent,
    PerformerComponent,
    GroupComponent,
    PerformersComponent,
    RecordComponent,
    RecordsComponent,
    UserRecordComponent,
    UserRecordsComponent,
    UserRegistrationComponent,
    LoginComponent,
    LogoutComponent,
    UserComponent
  ],
  imports: [
    BrowserModule.withServerTransition({ appId: 'ng-cli-universal' }),
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,

    BsDropdownModule.forRoot(),

    RouterModule.forRoot([
      { path: '', component: HomeComponent, pathMatch: 'full' },
      { path: 'group/:id', component: GroupComponent },
      { path: 'performers', component: PerformersComponent },
      { path: 'performer/:id', component: PerformerComponent },
      { path: 'records', component: RecordsComponent },
      { path: 'record/:id', component: RecordComponent },
      { path: 'user-records', component: UserRecordsComponent, canActivate: [canActivate] },
      { path: 'user-record/:id', component: UserRecordComponent, canActivate: [canActivate] },
      { path: 'login', component: LoginComponent },
      { path: 'logout', component: LogoutComponent },
      { path: 'registration', component: UserRegistrationComponent }, 
      { path: 'user', component: UserComponent },
    ]),
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
