import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RecordBrief } from '../../models/recordBrief';
import {APIService} from '../../Services/api';

@Component({
  selector: 'app-user-records',
  templateUrl: './user-records.component.html'
})

export class UserRecordsComponent {

  public personalRecords: RecordBrief[] = [];

  constructor(private route: ActivatedRoute, private router: Router, private apiService: APIService) {
    this.loadPersonalRecordsList();
  }

  private loadPersonalRecordsList() {
    this.personalRecords = this.apiService.getPersonalRecordsList();
  }

  public deleteFromUserCollection(personalRecordId: number) {
    const result = this.apiService.deleteFromUserCollection(personalRecordId);
  }

}
