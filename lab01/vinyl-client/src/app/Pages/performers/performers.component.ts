import { Component, Inject, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Performer } from '../../models/performer';
import { Group } from '../../models/group';

@Component({
  selector: 'app-performers',
  templateUrl: './performers.component.html',
})

export class PerformersComponent {

  private baseUrl: string = "";
  public performers: Performer[] = [];
  public groups: Group[] = [];

  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string, private route: ActivatedRoute, private router: Router) {
    this.baseUrl = baseUrl;
    this.loadGroupsList();
    this.loadPerformersList();
  }

  private loadGroupsList() {
    this.http.get<Group[]>(this.baseUrl + 'api/groups').subscribe(result => {
      this.groups = result.sort((a, b) => a.name.localeCompare(b.name));
    }, error => console.error(error));

    // test vals
    this.performers = [
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
  }

  private loadPerformersList() {
    this.http.get<Performer[]>(this.baseUrl + 'api/performers').subscribe(result => {
      this.performers = result.sort((a, b) => a.name.localeCompare(b.name));
    }, error => console.error(error));

    // test vals
    this.groups = [
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
  }
}
