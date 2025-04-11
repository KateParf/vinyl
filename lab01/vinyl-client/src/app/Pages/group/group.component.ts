import { Component, Inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Group } from '../../models/group';
import { RecordBrief } from '../../models/recordBrief';
import { Performer } from '../../models/performer';

@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
})


export class GroupComponent {
  private baseUrl: string = "";

  public group: Group | undefined = undefined;
  public groupId: number = 0;
  public groupName: string = "";
  public imageUrl: string = "";
  public performers?: Performer[] = [];
  public groupRecords: RecordBrief[] = [];


  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string, private route: ActivatedRoute, private router: Router) {
    this.baseUrl = baseUrl;
    void route.params.subscribe(params => this.groupId = params["id"]);
    this.group = {
        id: 1,
        name: 'The Beatles',
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
    this.groupName = this.group.name;
    this.imageUrl = this.group.image;
    this.performers = this.group.performers;
    //this.loadGroupData();
    this.loadGroupRecords();
  }

  private loadGroupData() {
    console.log("loadDanceData");
    this.http.get<Group>(this.baseUrl + 'api/group/' + this.groupId).subscribe(result => {
      this.group = result;

      this.groupName = this.group.name;
      this.imageUrl = this.group.image;
      this.performers = this.group.performers;
    }, error => console.error(error));
  }

  private loadGroupRecords() {
    // test vals
    this.groupRecords = [{
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
