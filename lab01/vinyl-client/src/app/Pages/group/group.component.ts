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
  public performers?: Performer[] = [];
  public groupRecords: RecordBrief[] = [];


  constructor(private route: ActivatedRoute, private router: Router, private apiService: APIService) {
    void route.params.subscribe(params => this.groupId = params["id"]);

    this.loadGroupData();
  }

  private loadGroupData() {
    this.group = this.apiService.getGroupById(this.groupId);

    this.groupName = this.group.name;
    this.imageUrl = this.group.image;
    this.performers = this.group.performers;
    
    this.loadGroupRecords();
  }

  private loadGroupRecords() {
    // test vals
    this.groupRecords = this.apiService.getRecordsByGroupId(this.groupId);
  }
}
