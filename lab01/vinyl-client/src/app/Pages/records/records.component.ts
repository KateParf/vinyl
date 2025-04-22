import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RecordBrief } from '../../models/recordBrief';
import { Performer } from '../../models/performer';
import { Group } from '../../models/group';
import { Genre } from '../../models/genre';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { APIService } from '../../Services/api';

@Component({
  selector: 'app-records',
  templateUrl: './records.component.html'
})

export class RecordsComponent {
  public records: RecordBrief[] = [];
  public queryGenreId: number | undefined = undefined;
  public queryGroupId: number | null = null;
  public queryPerformerId: number | null = null;
  public queryYear: number | null = null;
  public queryName: string = "";
  public queryBarcode: string = "";
  public genres: Genre[] = [];
  public groups: Group[] = [];
  public performers: Performer[] = [];

  formBarcode = new FormGroup({
    filterSearch: new FormControl("")
  });
  formName = new FormGroup({
    filterSearch: new FormControl("")
  });

  form = new FormGroup({
    genre_id: new FormControl(0, Validators.required),
    group_id: new FormControl(0, Validators.required),
    performer_id: new FormControl(0, Validators.required),
    decade: new FormControl(0, Validators.required)
  });

  constructor(private route: ActivatedRoute, private router: Router, private apiService: APIService) {
    this.loadRecordsList();
    this.loadGenresList();
    this.loadGroupsList();
    this.loadPerformersList();
  }

  private loadRecordsList() {
    // test vals
    this.records = this.apiService.getRecordsList();
  }

  ngOnInit() {
    // берем св-ва из адресной строки
    this.route.queryParams.subscribe(params => {
      this.queryGenreId = (params['genre_id']) ? Number(params['genre_id']) : undefined;
      this.queryGroupId = (params['group_id']) ? Number(params['group_id']) : null;
      this.queryPerformerId = (params['performer_id']) ? Number(params['performer_id']) : null;
      this.queryYear = (params['decade']) ? Number(params['decade']) : null;
    });
    this.route.queryParams.subscribe(params => {
      this.queryName = (params['name']) ? String(params['name']) : "";
    });
    this.formName.controls["filterSearch"].setValue(this.queryName);
    this.searchByName();
  }

  public resetFilter() {
    this.form.controls["genre_id"].setValue(0);
    this.form.controls["group_id"].setValue(0);
    this.form.controls["performer_id"].setValue(0);
    this.form.controls["decade"].setValue(0);
  }

  private loadGenresList() {
    this.genres = this.apiService.getGenresList();
  }

  private loadGroupsList() {
    this.groups = this.apiService.getGroupsList();
  }

  private loadPerformersList() {
    this.performers = this.apiService.getPerformersList();
  }

  public getRecordsWithFilters() {
    this.records = this.apiService.getRecordsWithFilters(
      this.form.value.genre_id,
      this.form.value.group_id,
      this.form.value.performer_id,
      this.form.value.decade
    );
  }

  public searchByBarcode() {
    if (this.formBarcode.value["filterSearch"]) {
      this.records = this.apiService.getRecordsByBarcode(this.formBarcode.value["filterSearch"]);
      this.queryBarcode = this.formBarcode.value["filterSearch"];
      this.queryName = "";
      this.formName.controls["filterSearch"].setValue("");
    }
  }

  public searchByName() {
    if (this.formName.value["filterSearch"]) {
      this.records = this.apiService.getRecordsByName(this.formName.value["filterSearch"]);
      this.queryName = this.formName.value["filterSearch"];
      this.queryBarcode = "";
      this.formBarcode.controls["filterSearch"].setValue("");
    }
  }

  public addToUserCollection(recordBrief: RecordBrief) {
    const record = this.apiService.addToUserCollectionRecord(recordBrief);
    const id = record.id;
    this.router.navigate(['/user-record/'+ id]);
  }

}
