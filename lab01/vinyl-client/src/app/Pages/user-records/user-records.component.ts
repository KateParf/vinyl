import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RecordBrief } from '../../models/recordBrief';
import {APIService} from '../../Services/api';
import { PersonalRecord } from '../../models/personalRecord';

@Component({
  selector: 'app-user-records',
  templateUrl: './user-records.component.html'
})

export class UserRecordsComponent {

  public personalRecords: RecordBrief[] = [];

  constructor(private route: ActivatedRoute, private router: Router, private apiService: APIService) {
    this.loadPersonalRecordsList();
  }

  ngOnInit() {this.loadPersonalRecordsList();}

  private async loadPersonalRecordsList() {
    await this.apiService.getUserRecordIds();
    this.personalRecords = await this.apiService.getPersonalRecordsList();
  }

  public deleteFromUserCollection(personalRecordId: number) {
    this.apiService.deleteFromUserCollection(personalRecordId);
    this.loadPersonalRecordsList();
    window.location.reload();
  }

}
