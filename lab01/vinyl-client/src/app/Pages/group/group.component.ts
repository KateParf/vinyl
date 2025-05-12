import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Group } from '../../models/group';
import { RecordBrief } from '../../models/recordBrief';
import { Performer } from '../../models/performer';
import { APIService } from '../../Services/api';

@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
})


export class GroupComponent {

  public group: Group | undefined = undefined;
  public groupId: number = 0;
  public groupName: string = "";
  public imageUrl: string = "";
  public performers: Performer[] = [];
  public groupRecords: RecordBrief[] = [];


  constructor(private route: ActivatedRoute, private router: Router, private apiService: APIService) {
    void route.params.subscribe(params => this.groupId = params["id"]);

    this.loadGroupData();
  }

  private async loadGroupData() {
    var group = await this.apiService.getGroupById(this.groupId);

    if (group != null) {
      this.group = group;
      this.groupName = this.group.name;
      this.imageUrl = this.group.picture;
      this.performers = this.group.performers != undefined ? this.group.performers : [];
    }
    await this.loadGroupRecords();
  }

  private async loadGroupRecords() {
    this.groupRecords = await this.apiService.getGroupRecords(this.groupId);
  }

}
