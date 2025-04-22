import { Component, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Record } from '../../models/record';
import { Performer } from '../../models/performer';
import { Track } from '../../models/track';
import { Cover } from '../../models/cover';
import { Group } from '../../models/group';
import { APIService } from '../../Services/api';

@Component({
  selector: 'app-record',
  templateUrl: './record.component.html'
})

export class RecordComponent {

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


  constructor(private route: ActivatedRoute, private router: Router, private apiService: APIService) {
    void route.params.subscribe(params => this.recordId = params["id"]);

    this.loadRecordData();
  }

  private loadRecordData() {
      this.record =  this.apiService.getRecord(this.recordId);

      this.recordName = this.record.name;
      this.recordYear = this.record.year;
      this.recordPublisher = this.record.publisher;
      this.recordBarcode = this.record.barcode;
      this.recordTracks = this.record.tracks;
      this.recordCovers = this.record.covers;
      this.recordPerformers = this.record.performers;
      this.recordGroups = this.record.groups;
  }

  public playTrack(trackId: number) {
    const res = this.apiService.getTrackURL(this.recordId, trackId);
    // Воспроизведение трека
    const audio = new Audio(res);
    audio.play();
  }

  public addToUserCollection(recordId: number) {
    // apiService.addToUserCollectionRecordId
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
