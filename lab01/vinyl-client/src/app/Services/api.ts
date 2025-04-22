import { Inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RecordBrief } from '../models/recordBrief';
import { Performer } from '../models/performer';
import { Group } from '../models/group';
import { Genre } from '../models/genre';
import {PersonalRecord} from '../models/personalRecord';


@Injectable({providedIn: 'root'})
export class APIService {

  private baseUrl: string = "";

  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string) {
    this.baseUrl = baseUrl;
  }

  //----

  public getGenresList(): Genre[] {
    /*
    this.http.get<Genre[]>(this.baseUrl + 'api/genres').subscribe(result => {
      this.genres = result;
    }, error => console.error(error));
    */

    // test vals
    const genres = [
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
    return genres;
  }

  //-- groups --

  public getGroupsList(): Group[] {
    /*
    this.http.get<Group[]>(this.baseUrl + 'api/groups').subscribe(result => {

    this.http.get<Group[]>(this.baseUrl + 'api/groups').subscribe(result => {
      this.groups = result;
    }, error => console.error(error));
    */

    // test vals
    const groups = [
      {
        id: 1,
        name: 'The Beatles',
        image: 'https://yastatic.net/naydex/yandex-search/i12tyh084/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8EzhrUAvS-GDd4ONfllzRcJ5UBuY-j45YZmTWWiIMl4fwHnEvp7cq7xFgRjPbz1HhTD'
      },
      {
        id: 2,
        name: 'Metallica',
        image: 'https://yastatic.net/naydex/yandex-search/i12tyh278/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8U1ibABvzWYDN8EPfpjyxwJ-jwLZeiwyIwLB0KiPsl7YhH6CfR1dZnpGQ5iIOqpGT7DOQ'
      },
      {
        id: 3,
        name: 'Daft Punk',
        image: 'https://yastatic.net/naydex/yandex-search/i12tyh084/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8E0ibUMuzOYBNcEOftgwRcA5UBuY-j45YZmTWWiIMl4fwHnEvp7cq7xFgRjPbz1HhTD'
      },
    ];
    return groups;
  }

  getGroupById(groupId: number): Group {
    /*
      this.http.get<Group>(this.baseUrl + 'api/group/' + groupId).subscribe(result => {
      //
      }, error => console.error(error));
    */

    return {
      id: 1,
      name: `The Beatles (group ${groupId})`,
      image: 'https://yastatic.net/naydex/yandex-search/i12tyh084/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8EzhrUAvS-GDd4ONfllzRcJ5UBuY-j45YZmTWWiIMl4fwHnEvp7cq7xFgRjPbz1HhTD',
      performers: [{
        id: 2,
        name: 'John Lennon',
        image: ''
      }, {
        id: 3,
        name: 'Paul McCartney',
        image: ''
      }, {
        id: 4,
        name: 'George Harrison',
        image: ''
      }, {
        id: 5,
        name: 'Ringo Starr',
        image: ''
      }]
    }
  }

  //---- performers

  public getPerformersList(): Performer[] {
    /*
    this.http.get<Performer[]>(this.baseUrl + 'api/performers').subscribe(result => {
      this.performers = result;
    }, error => console.error(error));
    */

    // test vals
    const performers = [
      {
        id: 1,
        name: 'Miles Davis',
        image: 'https://yastatic.net/naydex/yandex-search/i12tyh278/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8IygbANujGYDN4HPv9izBQK_DwLZeiwyIwLB0KiPsl7YhH6CfR1dZnpGQ5iIOqpGT7DOQ'
      },
      {
        id: 2,
        name: 'John Lennon',
        image: 'https://yastatic.net/naydex/yandex-search/i12tyh084/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8Q2grQMvjeYBNsEO_9gyBYO5UBuY-j45YZmTWWiIMl4fwHnEvp7cq7xFgRjPbz1HhTD'
      },
      {
        id: 3,
        name: 'Paul McCartney',
        image: 'https://yastatic.net/naydex/yandex-search/i11tyh987/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8MxgrAOoDGGDtwEOfVhyBAXmSVoY6DV7-ssamW8IMplbxz8HPR8Rbb-HAV-a-DyNBQ'
      }
    ];
    return performers;
  }

  public getPerformerById(performerId: number): Performer {
    /*
    this.http.get<Performer>(this.baseUrl + 'api/performer/' + performerId).subscribe(result => {
    //
    }, error => console.error(error));

    */
    return {
      id: 3,
      name: `Paul McCartney (performer ${performerId})`,
      image: 'https://yastatic.net/naydex/yandex-search/i11tyh987/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8MxgrAOoDGGDtwEOfVhyBAXmSVoY6DV7-ssamW8IMplbxz8HPR8Rbb-HAV-a-DyNBQ',
      groups: [{
        id: 1,
        name: 'The Beatles',
        image: 'https://yastatic.net/naydex/yandex-search/i12tyh084/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8EzhrUAvS-GDd4ONfllzRcJ5UBuY-j45YZmTWWiIMl4fwHnEvp7cq7xFgRjPbz1HhTD'
      },
      {
        id: 4,
        name: 'Wings',
        image: 'https://yastatic.net/naydex/yandex-search/SFH6hB200/2c630d3Sq/320yYPO8rn0CSlYfPuacw1SPcU33dm2EHJcofPdKYaO9TGlv8yz-oEGSL304V5gw65l3zR6i7qiBCCiWZ-ada5NVysxzcGu5ipK_u_5awNd4_l0U228FZPQ'
      }]
    };
  }


  // records brief -----

  public getRecordsList(): RecordBrief[] {
    //this.http.get<RecordBrief[]>(this.baseUrl + 'api/records/list').subscribe(result => {
    //  const records = result;
    // return records;
    //}, error => console.error(error));

    // test vals
    const records = [
      {
        title: 'The Dark Side of the Moon',
        year: 1973,
        genre: 'Rock',
        coverUrl: 'https://yastatic.net/naydex/yandex-search/i11tyh308/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8Y4iLQOoDmBCNYPNfhj1ncO-iMgBoff8ZgWanWvKM9zbjfUNdlXRPniDT9Z',
        sourceUID: null,
        barcode: null,
        id: 1
      },
      {
        title: 'Kind of Blue',
        year: 1959,
        genre: 'Jazz',
        coverUrl: 'https://yastatic.net/naydex/yandex-search/i11tyh502/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8Y5g7QAoDGHCtkHP_hnwQtr_CNoK43f75gVd2WyM8F9aQDMOtNWWa--ChVZGg',
        sourceUID: null,
        barcode: null,
        id: 2
      },
      {
        title: 'Abbey Road',
        year: 1969,
        genre: 'Rock',
        coverUrl: 'https://yastatic.net/naydex/yandex-search/i11tyh308/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8cxgrIIoDmOCNgBOPpj1ncO-iMgBoff8ZgWanWvKM9zbjfUNdlXRPniDT9Z',
        sourceUID: null,
        barcode: null,
        id: 3
      },
      {
        title: 'Random Access Memories',
        year: 2013,
        genre: 'Electronic',
        coverUrl: 'https://yastatic.net/naydex/yandex-search/i11tyh502/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8UxiLQKoDKEDNoGNP9jzAtr_CNoK43f75gVd2WyM8F9aQDMOtNWWa--ChVZGg',
        sourceUID: null,
        barcode: null,
        id: 4
      },
      {
        title: '…And Justice for All',
        year: 1988,
        genre: 'Rock',
        coverUrl: 'https://yastatic.net/naydex/yandex-search/i11tyh696/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8Eyg7IIuzeYDt0CNPtiwBEP5UBuY-j45YYLdHu_PsdoaQn8FcRTUrP1AE50MM3Y',
        sourceUID: null,
        barcode: null,
        id: 5
      }];

    return records;
  }


  public getRecordsWithFilters(genre_id?: number | null, group_id?: number | null, performer_id?: number | null, decade?: number | null): RecordBrief[] {
    let filterParams = "?";

    if (genre_id) filterParams += "&genre_id=" + genre_id;
    if (group_id) filterParams += "&group_id=" + group_id;
    if (performer_id) filterParams += "&performer_id=" + performer_id;
    if (decade) filterParams += "&decade=" + decade;

    /*
    this.http.get<RecordBrief[]>(this.baseUrl + 'api/records/list' + filterParams).subscribe(result => {
      this.records = result;
    }, error => console.error(error));
    */

    // test data
    const recs = this.getRecordsList();
    return [recs[3], recs[4] ];
  }

  public getRecordsByBarcode(barcode: string): RecordBrief[] {

    /*
      this.http.post<any>("/api/records/search/barcode", barcode)
        .subscribe(res => {
          alert('Record finded SUCCESFUL');
        }, err => {
          alert("Something went wrong")
        })
    */

    // test data
    const recs = this.getRecordsList();
    return [recs[1], recs[2] ].map(r => {r.title += ' (bar)'; return r;}).concat([{
      title: 'discogs…And Justice for All',
      year: 1988,
      genre: 'Rock',
      coverUrl: 'https://yastatic.net/naydex/yandex-search/i11tyh696/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8Eyg7IIuzeYDt0CNPtiwBEP5UBuY-j45YYLdHu_PsdoaQn8FcRTUrP1AE50MM3Y',
      sourceUID: "https://api.discogs.com/releases/10811401",
      barcode: null,
      id: null
    }]);
  }

  public getRecordsByName(name: string): RecordBrief[] {
    /*
    this.http.post<any>("/api/records/search/name", name)
      .subscribe(res => {
        alert('Record finded SUCCESFUL');
      }, err => {
        alert("Something went wrong")
      })
    */

    // test data
    const recs = this.getRecordsList();
    return [recs[2], recs[3] ].map(r => {r.title += ' (name)'; return r;});
  }

  getRecordsByGroupId(groupId: number): RecordBrief[] {
    //!! TODO - потом вызывать норм метод фильтраии по групИд

    return [{
      title: 'Abbey Road 1',
      year: 1969,
      genre: 'Rock',
      coverUrl: 'https://yastatic.net/naydex/yandex-search/i11tyh308/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8cxgrIIoDmOCNgBOPpj1ncO-iMgBoff8ZgWanWvKM9zbjfUNdlXRPniDT9Z',
      sourceUID: '',
      barcode: '',
      id: 3
    },
    {
      title: 'Abbey Road 2',
      year: 1969,
      genre: 'Rock',
      coverUrl: 'https://yastatic.net/naydex/yandex-search/i11tyh308/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8cxgrIIoDmOCNgBOPpj1ncO-iMgBoff8ZgWanWvKM9zbjfUNdlXRPniDT9Z',
      sourceUID: '',
      barcode: '',
      id: 3
    },
    {
      title: 'Abbey Road 3',
      year: 1969,
      genre: 'Rock',
      coverUrl: 'https://yastatic.net/naydex/yandex-search/i11tyh308/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8cxgrIIoDmOCNgBOPpj1ncO-iMgBoff8ZgWanWvKM9zbjfUNdlXRPniDT9Z',
      sourceUID: '',
      barcode: '',
      id: 3
    },
    {
      title: 'Abbey Road 4',
      year: 1969,
      genre: 'Rock',
      coverUrl: 'https://yastatic.net/naydex/yandex-search/i11tyh308/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8cxgrIIoDmOCNgBOPpj1ncO-iMgBoff8ZgWanWvKM9zbjfUNdlXRPniDT9Z',
      sourceUID: '',
      barcode: '',
      id: 3
    }
    ];
  }

  getRecord(recordId: number): import("../models/record").Record {
    /*
    this.http.get<Record>(this.baseUrl + 'api/records/get/' + recordId).subscribe(result => {
      //
    }, error => console.error(error));   
    */

    return {
      id: 1,
      name: `Abbey Road (rec ${recordId})`,
      year: 1969,
      publisher: "Apple Records",
      barcode: "111222333",
      tracks: [
        {
          id: 1,
          name: 'Come Together'
        },
        {
          id: 2,
          name: 'Octopuses garden'
        },
        {
          id: 3,
          name: 'You never give me your money'
        }
      ],
      covers: [
        {
          id: 1,
          image: 'https://avatars.mds.yandex.net/i?id=6f36d6c01a938a4cc8141321bd74bf71_l-7755581-images-thumbs&n=13'
        },
        {
          id: 2,
          image: 'https://yastatic.net/naydex/yandex-search/i11tyh308/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8cxgrIIoDmOCNgBOPpj1ncO-iMgBoff8ZgWanWvKM9zbjfUNdlXRPniDT9Z'
        }
      ],
      performers: [],
      groups: [{
        id: 1,
        name: 'The Beatles',
        image: 'https://yastatic.net/naydex/yandex-search/i12tyh084/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8EzhrUAvS-GDd4ONfllzRcJ5UBuY-j45YZmTWWiIMl4fwHnEvp7cq7xFgRjPbz1HhTD'
      }]
    }
  }

  //---

  getPersonalRecordsList(): RecordBrief[] {
    /*
    this.http.get<RecordBrief[]>(this.baseUrl + 'api/userrecords/list').subscribe(result => {
    }, error => console.error(error));
    */
   
    // test data
    const recs = this.getRecordsList();
    return [recs[0], recs[2], recs[4] ].map(r => {r.title += ' (my)'; return r;});
  }

  getPersonalRecordById(personalRecordId: number): PersonalRecord  {
    /*
    this.http.get<PersonalRecord>(this.baseUrl + 'api/userrecords/get' + personalRecordId).subscribe(result => {
    ///
    }, error => console.error(error));
    */

    // test data
    return {
      id: 1,
      condition: "BAD",
      comment: `Ваще пластинка огонь где стоит не знаю`,
      record: {
        id: 1,
        name: `Abbey Road (pers id ${personalRecordId})`,
        year: 1969,
        publisher: "Apple Records",
        barcode: "111222333",
        tracks: [
          {
            id: 1,
            name: 'Come Together'
          },
          {
            id: 2,
            name: 'Octopuses garden'
          },
          {
            id: 3,
            name: 'You never give me your money'
          }
        ],
        covers: [
          {
            id: 1,
            image: 'https://avatars.mds.yandex.net/i?id=6f36d6c01a938a4cc8141321bd74bf71_l-7755581-images-thumbs&n=13'
          },
          {
            id: 2,
            image: 'https://yastatic.net/naydex/yandex-search/i11tyh308/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8cxgrIIoDmOCNgBOPpj1ncO-iMgBoff8ZgWanWvKM9zbjfUNdlXRPniDT9Z'
          }
        ],
        performers: [],
        groups: [{
          id: 1,
          name: 'The Beatles',
          image: 'https://yastatic.net/naydex/yandex-search/i12tyh084/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8EzhrUAvS-GDd4ONfllzRcJ5UBuY-j45YZmTWWiIMl4fwHnEvp7cq7xFgRjPbz1HhTD'
        }]
      }
    };
  }

  //---

  public getTrackURL(recordId: number, trackId: number): string {
    //this.http.get<string>(this.baseUrl + 'api/' + recordId + '/play/' + trackId).subscribe(result => {
    //
    //}, error => console.error(error));
    return "";
  }


  //----

  public addToUserCollectionRecord(record: RecordBrief): PersonalRecord {
    /*
    this.http.post<any>("/api/userrecords/addbrief", record)
      .subscribe(res => {
        alert('Record added SUCCESFUL');
      }, err => {
        alert("Something went wrong")
      })
      */
     return this.getPersonalRecordById(0);
  }

  public addToUserCollectionRecordId(recordId: number) {
  //  this.http.get<string>(this.baseUrl + 'api/records/add/' + recordId).subscribe(result => {
  //}, error => console.error(error));
  }

  public editUserCollectionRecord(record: PersonalRecord) {
    /*
    this.http.post<any>("/api/userrecords/edit", record)
      .subscribe(res => {
        alert('Record added SUCCESFUL');
      }, err => {
        alert("Something went wrong")
      })
      */
  }

  public deleteFromUserCollection(personalRecordId: number) {
    //  this.http.get<string>(this.baseUrl + 'api/userrecords/delete/' + personalRecordId).subscribe(result => {
  //}, error => console.error(error));
  }

  // ---
  public isAuth() {

  }

  public login(login: string, pass) {

  }

}
