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
    this.personalRecords = this.fromRecordToBrief(await this.apiService.getPersonalRecordsList());
  }

  public deleteFromUserCollection(personalRecordId: number) {
    this.apiService.deleteFromUserCollection(personalRecordId);
    this.loadPersonalRecordsList();
  }

  private fromRecordToBrief(records: PersonalRecord[]) {
      var res_records = [];
      for (let i = 0; i < records.length; i++) {
        var recordBrief = {
          id: records[i].id,
          title: records[i].record.name,
          year: records[i].record.year,
          genre: records[i].record.genre.name,
          coverUrl: records[i].record.covers.sort((el1,el2) => el1.id - el2.id)[0].picture,
          sourceUID: null,
          barcode: records[i].record.barcode
        };
        res_records.push(recordBrief);
      }
      return res_records;
    }

}
