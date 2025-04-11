import { Component, Inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { RecordBrief } from '../../models/recordBrief';

@Component({
  selector: 'app-user-records',
  templateUrl: './user-records.component.html'
})

export class UserRecordsComponent {

  private baseUrl: string = "";
  public personalRecords: RecordBrief[] = [];

  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string, private route: ActivatedRoute, private router: Router) {
    this.baseUrl = baseUrl;
    this.loadPersonalRecordsList();
  }

  private loadPersonalRecordsList() {
    this.http.get<RecordBrief[]>(this.baseUrl + 'api/userrecords/list').subscribe(result => {
      this.personalRecords = result;
    }, error => console.error(error));

    // testVals
    this.personalRecords = [
      {
        title: 'The Dark Side of the Moon',
        year: 1973,
        genre: 'Rock',
        coverUrl: 'https://yastatic.net/naydex/yandex-search/i11tyh308/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8Y4iLQOoDmBCNYPNfhj1ncO-iMgBoff8ZgWanWvKM9zbjfUNdlXRPniDT9Z',
        sourceUID: '',
        barcode: ''
      },
      {
        title: 'Kind of Blue',
        year: 1959,
        genre: 'Jazz',
        coverUrl: 'https://yastatic.net/naydex/yandex-search/i11tyh502/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8Y5g7QAoDGHCtkHP_hnwQtr_CNoK43f75gVd2WyM8F9aQDMOtNWWa--ChVZGg',
        sourceUID: '',
        barcode: ''
      },
      {
        title: 'Abbey Road',
        year: 1969,
        genre: 'Rock',
        coverUrl: 'https://yastatic.net/naydex/yandex-search/i11tyh308/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8cxgrIIoDmOCNgBOPpj1ncO-iMgBoff8ZgWanWvKM9zbjfUNdlXRPniDT9Z',
        sourceUID: '',
        barcode: ''
      }];
  }

}
