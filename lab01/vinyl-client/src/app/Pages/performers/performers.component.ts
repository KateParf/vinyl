import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Performer } from '../../models/performer';
import { Group } from '../../models/group';
import {APIService} from '../../Services/api';

@Component({
  selector: 'app-performers',
  templateUrl: './performers.component.html',
})

export class PerformersComponent {

  public performers: Performer[] = [];
  public groups: Group[] = [];

  constructor(private route: ActivatedRoute, private router: Router, private apiService: APIService ) {
    this.loadGroupsList();
    this.loadPerformersList();
  }

  private async loadGroupsList() {
    const result = await this.apiService.getGroupsList();
    this.groups = result.sort((a, b) => a.name.localeCompare(b.name));
  }

  private async loadPerformersList() {
    const result = await this.apiService.getPerformersList();
    this.performers = result.sort((a, b) => a.name.localeCompare(b.name));
  }
}
