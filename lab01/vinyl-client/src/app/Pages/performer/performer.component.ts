import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Performer } from '../../models/performer';
import { Group } from '../../models/group';
import { RecordBrief } from '../../models/recordBrief';
import {APIService} from '../../Services/api';

@Component({
  selector: 'app-performer',
  templateUrl: './performer.component.html',
})


export class PerformerComponent {
  public performer: Performer | undefined = undefined;
  public performerId: number = 0;
  public performerName: string = "";
  public imageUrl: string = "";
  public groups?: Group[] = [];

  public performerRecords: RecordBrief[] = [];

  constructor(private route: ActivatedRoute, private router: Router, private apiService: APIService) {
    void route.params.subscribe(params => this.performerId = params["id"]);

    this.loadPerformerData();
  }


  private loadPerformerData() {
    this.performer = this.apiService.getPerformerById(this.performerId);

    this.performerName = this.performer.name;
    this.imageUrl = this.performer.image;
    this.groups = this.performer.groups;

    this.loadPerformerRecords();
  }

  private loadPerformerRecords() {
    // test vals
    this.performerRecords = [{
      title: 'Abbey Road',
      year: 1969,
      genre: 'Rock',
      coverUrl: 'https://yastatic.net/naydex/yandex-search/i11tyh308/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8cxgrIIoDmOCNgBOPpj1ncO-iMgBoff8ZgWanWvKM9zbjfUNdlXRPniDT9Z',
      sourceUID: '',
      barcode: '',
      id: 3
    },
    {
      title: 'Abbey Road',
      year: 1969,
      genre: 'Rock',
      coverUrl: 'https://yastatic.net/naydex/yandex-search/i11tyh308/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8cxgrIIoDmOCNgBOPpj1ncO-iMgBoff8ZgWanWvKM9zbjfUNdlXRPniDT9Z',
      sourceUID: '',
      barcode: '',
      id: 3
    },
    {
      title: 'Abbey Road',
      year: 1969,
      genre: 'Rock',
      coverUrl: 'https://yastatic.net/naydex/yandex-search/i11tyh308/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8cxgrIIoDmOCNgBOPpj1ncO-iMgBoff8ZgWanWvKM9zbjfUNdlXRPniDT9Z',
      sourceUID: '',
      barcode: '',
      id: 3
    },
    {
      title: 'Abbey Road',
      year: 1969,
      genre: 'Rock',
      coverUrl: 'https://yastatic.net/naydex/yandex-search/i11tyh308/a3e3e896_R/rzVBW48SpldEPZsi3FxYsuhWDmMxveAOP7EDHRWinsHIVwdMiA3F-8cxgrIIoDmOCNgBOPpj1ncO-iMgBoff8ZgWanWvKM9zbjfUNdlXRPniDT9Z',
      sourceUID: '',
      barcode: '',
      id: 3
    }

    ]
  }
}

