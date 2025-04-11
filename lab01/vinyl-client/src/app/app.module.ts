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
import { AuthorizationComponent } from './Pages/authorization/authorization.component';
import { LoginComponent } from './Pages/login/login.component';
import { ChangePasswordComponent } from './Pages/change-password/change-password.component';

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
    AuthorizationComponent,
    LoginComponent,
    ChangePasswordComponent
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
      { path: 'performer', component: PerformerComponent },
      { path: 'group', component: GroupComponent },
      { path: 'performers', component: PerformersComponent },
      { path: 'record', component: RecordComponent },
      { path: 'records', component: RecordsComponent },
      { path: 'user-record', component: UserRecordComponent },
      { path: 'user-records', component: UserRecordsComponent },
      { path: 'login', component: LoginComponent },
      { path: 'auth', component: AuthorizationComponent }, 
      { path: 'change-pass', component: ChangePasswordComponent },
    ]),
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
