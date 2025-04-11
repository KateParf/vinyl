import { Component, Inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Performer } from '../../models/performer';
import { Group } from '../../models/group';
import { RecordBrief } from '../../models/recordBrief';

@Component({
  selector: 'app-performer',
  templateUrl: './performer.component.html',
})


export class PerformerComponent {
  private baseUrl: string = "";

  public performer: Performer | undefined = undefined;
  public performerId: number = 0;
  public performerName: string = "";
  public imageUrl: string = "";
  public groups?: Group[] = [];

  public performerRecords: RecordBrief[] = [];


  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string, private route: ActivatedRoute, private router: Router) {
    this.baseUrl = baseUrl;
    void route.params.subscribe(params => this.performerId = params["id"]);
    // test vals
    this.performer =
    {
      id: 3,
      name: 'Paul McCartney',
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
    this.performerName = this.performer.name;
    this.imageUrl = this.performer.image;
    this.groups = this.performer.groups;
    //this.loadPerformerData();
    this.loadPerformerRecords();
  }


  private loadPerformerData() {
    this.http.get<Performer>(this.baseUrl + 'api/performer/' + this.performerId).subscribe(result => {
      this.performer = result;

      this.performerName = this.performer.name;
      this.imageUrl = this.performer.image;
      this.groups = this.performer.groups;
    }, error => console.error(error));
  }

  private loadPerformerRecords() {
    // test vals
    this.performerRecords = [{
      title: 'Abbey Road',
      year: 1969,
      genre: 'Rock',
      coverUrl: 'https://yastatic.net/naydex/yandex-search/i11tyh308/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8cxgrIIoDmOCNgBOPpj1ncO-iMgBoff8ZgWanWvKM9zbjfUNdlXRPniDT9Z',
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
      title: 'Abbey Road',
      year: 1969,
      genre: 'Rock',
      coverUrl: 'https://yastatic.net/naydex/yandex-search/i11tyh308/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8cxgrIIoDmOCNgBOPpj1ncO-iMgBoff8ZgWanWvKM9zbjfUNdlXRPniDT9Z',
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
    }

    ]
  }
}

