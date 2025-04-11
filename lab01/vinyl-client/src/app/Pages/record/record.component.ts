import { Component, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Record } from '../../models/record';
import { Performer } from '../../models/performer';
import { Track } from '../../models/track';
import { Cover } from '../../models/cover';
import { Group } from '../../models/group';

@Component({
  selector: 'app-record',
  templateUrl: './record.component.html'
})

export class RecordComponent {
  private baseUrl: string = "";

  public record: Record | undefined = undefined;
  public recordId: number = 0;
  public recordName: string = "";
  public recordYear: number = 0;
  public recordPublisher: string = "";
  public recordBarcode: string = "";
  public recordTracks: Track[] = [];
  public recordCovers: Cover[] = [];
  public recordPerformers: Performer[] = [];
  public recordGroups: Group[] = [];


  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string, private route: ActivatedRoute, private router: Router) {
    this.baseUrl = baseUrl;
    void route.params.subscribe(params => this.recordId = params["id"]);

    // testVals
    this.record = {
      id: 1,
      name: 'Abbey Road',
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
    // ---
    this.recordName = this.record.name;
    this.recordYear = this.record.year;
    this.recordPublisher = this.record.publisher;
    this.recordBarcode = this.record.barcode;
    this.recordTracks = this.record.tracks;
    this.recordCovers = this.record.covers;
    this.recordPerformers = this.record.performers;
    this.recordGroups = this.record.groups;
    //this.loadRecordData();
  }

  private loadRecordData() {
    this.http.get<Record>(this.baseUrl + 'api/records/get/' + this.recordId).subscribe(result => {
      this.record = result;

      this.recordName = this.record.name;
      this.recordYear = this.record.year;
      this.recordPublisher = this.record.publisher;
      this.recordBarcode = this.record.barcode;
      this.recordTracks = this.record.tracks;
      this.recordCovers = this.record.covers;
      this.recordPerformers = this.record.performers;
      this.recordGroups = this.record.groups;
    }, error => console.error(error));

  }

  public playTrack(trackId: number) {
    this.http.get<string>(this.baseUrl + 'api/' + this.recordId + '/play/' + trackId).subscribe(result => {
      // Воспроизведение трека
      const audio = new Audio(result);
      audio.play();
    }, error => console.error(error));
  }

  // -- работа с каруселью
  activeCoverIndex = 0;
  nextCover() {
    this.activeCoverIndex = this.record != undefined?(this.activeCoverIndex + 1) % this.record.covers.length:0;
  }
  prevCover() {
    this.activeCoverIndex = this.record != undefined?(this.activeCoverIndex - 1 + this.record.covers.length) % this.record.covers.length:0;
  }
}
