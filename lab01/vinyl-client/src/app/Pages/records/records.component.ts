import { Component, Inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { RecordBrief } from '../../models/recordBrief';
import { Performer } from '../../models/performer';
import { Group } from '../../models/group';
import { Genre } from '../../models/genre';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-records',
  templateUrl: './records.component.html'
})

export class RecordsComponent {
  private baseUrl: string = "";
  public records: RecordBrief[] = [];
  public queryGenreId: number | null = null;
  public queryGroupId: number | null = null;
  public queryPerformerId: number | null = null;
  public queryYear: number | null = null;
  public queryName: String | null = null;
  public genres: Genre[] = [];
  public groups: Group[] = [];
  public performers: Performer[] = [];

  form = new FormGroup({
    genre_id: new FormControl(0, Validators.required),
    group_id: new FormControl(0, Validators.required),
    performer_id: new FormControl(0, Validators.required),
    decade: new FormControl(0, Validators.required)
  });

  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string, private route: ActivatedRoute, private router: Router) {
    this.baseUrl = baseUrl;
    this.loadRecordsList();
    this.loadGenresList();
    this.loadGroupsList();
    this.loadPerformersList();
  }

  private loadRecordsList() {
    this.http.get<RecordBrief[]>(this.baseUrl + 'api/records/list').subscribe(result => {
      this.records = result;
    }, error => console.error(error));

    // test vals
    this.records = [
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
      },
      {
        title: 'Random Access Memories',
        year: 2013,
        genre: 'Electronic',
        coverUrl: 'https://yastatic.net/naydex/yandex-search/i11tyh502/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8UxiLQKoDKEDNoGNP9jzAtr_CNoK43f75gVd2WyM8F9aQDMOtNWWa--ChVZGg',
        sourceUID: '',
        barcode: ''
      },
      {
        title: '…And Justice for All',
        year: 1988,
        genre: 'Rock',
        coverUrl: 'https://yastatic.net/naydex/yandex-search/i11tyh696/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8Eyg7IIuzeYDt0CNPtiwBEP5UBuY-j45YYLdHu_PsdoaQn8FcRTUrP1AE50MM3Y',
        sourceUID: '',
        barcode: ''
      }];
  }

  private loadGenresList() {
    this.http.get<Genre[]>(this.baseUrl + 'api/genres').subscribe(result => {
      this.genres = result;
    }, error => console.error(error));

    // test vals
    this.genres = [
      {
        id: 1,
        name: 'Rock'
      },
      {
        id: 2,
        name: 'Electronic'
      },
      {
        id: 3,
        name: 'Jazz'
      },
    ];
  }

  private loadGroupsList() {
    this.http.get<Group[]>(this.baseUrl + 'api/groups').subscribe(result => {
      this.groups = result;
    }, error => console.error(error));

    // test vals
    this.groups = [
      {
        id: 1,
        name: 'The Beatles',
        image: ''
      },
      {
        id: 2,
        name: 'Metallica',
        image: ''
      },
      {
        id: 3,
        name: 'Daft Punk',
        image: ''
      },
    ];
  }

  private loadPerformersList() {
    this.http.get<Performer[]>(this.baseUrl + 'api/performers').subscribe(result => {
      this.performers = result;
    }, error => console.error(error));

    // test vals
    this.performers = [
      {
        id: 1,
        name: 'Miles Davis',
        image: ''
      }
    ];
  }

  public getRecordsWithFilters() {
    // берем св-ва из адресной строки
    this.route.queryParams.subscribe(params => {
      this.queryGenreId = (params['genre_id']) ? Number(params['genre_id']) : null;
      this.queryGroupId = (params['group_id']) ? Number(params['group_id']) : null;
      this.queryPerformerId = (params['performer_id']) ? Number(params['performer_id']) : null;
      this.queryYear = (params['decade']) ? Number(params['decade']) : null;
    });

    let filterParams = "?";

    if (this.form.value.genre_id) filterParams += "&genre_id=" + this.form.value.genre_id;
    if (this.form.value.group_id) filterParams += "&group_id=" + this.form.value.group_id;
    if (this.form.value.performer_id) filterParams += "&performer_id=" + this.form.value.performer_id;
    if (this.form.value.decade) filterParams += "&decade=" + this.form.value.decade;


    this.http.get<RecordBrief[]>(this.baseUrl + 'api/records/list' + filterParams).subscribe(result => {
      this.records = result;
    }, error => console.error(error));
  }

}
