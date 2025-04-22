import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Record } from '../../models/record';
import { Performer } from '../../models/performer';
import { Track } from '../../models/track';
import { Cover } from '../../models/cover';
import { Group } from '../../models/group';
import { PersonalRecord } from '../../models/personalRecord';
import { APIService } from '../../Services/api';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-user-record',
  templateUrl: './user-record.component.html'
})

export class UserRecordComponent {
  private baseUrl: string = "";
  public personalRecord: PersonalRecord | undefined = undefined;
  public personalRecordId: number = 0;
  public condition: string = "";
  public comment: string = "";

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

  form = new FormGroup({
    commentBody: new FormControl(""),
    condition: new FormControl("NEW"),
  });

  constructor(private route: ActivatedRoute, private router: Router, private apiService: APIService) {
    void route.params.subscribe(params => this.personalRecordId = params["id"]);

    this.loadPersonalRecordData();
  }

  private loadPersonalRecordData() {
    this.personalRecord = this.apiService.getPersonalRecordById(this.personalRecordId);
    // ---
    this.condition = this.personalRecord.condition;
    this.comment = this.personalRecord.comment;
    this.record = this.personalRecord.record;
    this.recordName = this.record.name;
    this.recordYear = this.record.year;
    this.recordPublisher = this.record.publisher;
    this.recordBarcode = this.record.barcode;
    this.recordTracks = this.record.tracks;
    this.recordCovers = this.record.covers;
    this.recordPerformers = this.record.performers;
    this.recordGroups = this.record.groups;

    this.form.controls["commentBody"].setValue(this.comment);
    this.form.controls["condition"].setValue(this.condition);
  }

  onSubmit() {
    const data = this.form.value;
    console.log(data);
    this.comment = data.commentBody ? data.commentBody : "";
    this.condition = data.condition ? data.condition : "";
    if (this.personalRecord != undefined) this.personalRecord.comment = this.comment;
    if (this.personalRecord != undefined) this.personalRecord.condition = this.condition;
    if (this.personalRecord != undefined) this.apiService.editUserCollectionRecord(this.personalRecord);
  }

  public deleteFromUserCollection(personalRecordId: number) {
    const result = this.apiService.deleteFromUserCollection(personalRecordId);
  }

  public playTrack(trackId: number) {
    const result = this.apiService.getTrackURL(this.recordId, trackId);
    // Воспроизведение трека
    const audio = new Audio(result);
    audio.play();
  }

  // -- работа с каруселью
  activeCoverIndex = 0;
  nextCover() {
    this.activeCoverIndex = this.record != undefined ? (this.activeCoverIndex + 1) % this.record.covers.length : 0;
  }
  prevCover() {
    this.activeCoverIndex = this.record != undefined ? (this.activeCoverIndex - 1 + this.record.covers.length) % this.record.covers.length : 0;
  }
}
