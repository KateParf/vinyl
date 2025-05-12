import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Performer } from '../../models/performer';
import { Group } from '../../models/group';
import { RecordBrief } from '../../models/recordBrief';
import { APIService } from '../../Services/api';

@Component({
  selector: 'app-performer',
  templateUrl: './performer.component.html',
})


export class PerformerComponent {
  public performer: Performer | undefined = undefined;
  public performerId: number = 0;
  public performerName: string = "";
  public imageUrl: string = "";
  public group?: Group | undefined = undefined;

  public performerRecords: RecordBrief[] = [];

  constructor(private route: ActivatedRoute, private router: Router, private apiService: APIService) {
    void route.params.subscribe(params => this.performerId = params["id"]);
    
    this.loadPerformerData();
  }

  private async loadPerformerData() {
    var performer = await this.apiService.getPerformerById(this.performerId);

    if (performer != null) {
      this.performer = performer;
      this.performerName = this.performer.name;
      this.imageUrl = this.performer.picture;
      this.group = this.performer.group;
    }
    
    await this.loadPerformerRecords();
  }

  private async loadPerformerRecords() {
    if (this.performer)
      this.performerRecords = await this.apiService.getPerformerRecords(this.performer.id);
  }
}

