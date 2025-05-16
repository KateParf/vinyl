import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RecordBrief } from '../../models/recordBrief';
import { Performer } from '../../models/performer';
import { Group } from '../../models/group';
import { Genre } from '../../models/genre';
import { Record } from '../../models/record';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { APIService } from '../../Services/api';
import { AuthService } from '../../Services/AuthService';

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

  public decades = [{ value: 1950, name: "1950-е" }, { value: 1960, name: "1960-е" }, { value: 1970, name: "1970-е" }, { value: 1980, name: "1980-е" }, { value: 1990, name: "1990-е" }, { value: 2000, name: "2000-е" }, { value: 2010, name: "2010-е" }, { value: 2020, name: "2020-е" }];

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

  constructor(private route: ActivatedRoute, private router: Router, private authService: AuthService, private apiService: APIService) {
    this.loadRecordsList();
    this.loadGenresList();
    this.loadGroupsList();
    this.loadPerformersList();
  }

  private async loadRecordsList() {
    this.records = await this.apiService.getRecordsList();
  }

  ngOnInit() {
    // берем св-ва из адресной строки
    this.route.queryParams.subscribe(params => {
      this.queryGenreId = (params['genre_id']) ? Number(params['genre_id']) : undefined;
      this.queryGroupId = (params['group_id']) ? Number(params['group_id']) : null;
      this.queryPerformerId = (params['performer_id']) ? Number(params['performer_id']) : null;
      this.queryYear = (params['decade']) ? Number(params['decade']) : null;
    });
  }

  public resetFilter() {
    this.form.controls["genre_id"].setValue(0);
    this.form.controls["group_id"].setValue(0);
    this.form.controls["performer_id"].setValue(0);
    this.form.controls["decade"].setValue(0);
    //this.loadRecordsList();
  }

  public resetBarcode() {
    this.queryBarcode = "";
    this.formBarcode.controls["filterSearch"].setValue("");
  }

  public resetName() {
    this.queryName = "";
    this.formName.controls["filterSearch"].setValue("");
  }

  private async loadGenresList() {
    this.genres = await this.apiService.getGenresList();
  }

  private async loadGroupsList() {
    this.groups = await this.apiService.getGroupsList();
  }

  private async loadPerformersList() {
    this.performers = await this.apiService.getPerformersList();
  }

  public async getRecordsWithFilters() {
    this.resetName();
    this.resetBarcode();
    this.records = await this.apiService.getRecordsWithFilters(
      this.form.value.genre_id,
      this.form.value.group_id,
      this.form.value.performer_id,
      this.form.value.decade
    );
  }

  public async searchByBarcode() {
    this.resetFilter();
    this.resetName();
    if (this.formBarcode.value["filterSearch"]) {
      this.records = await this.apiService.getRecordsByBarcode(this.formBarcode.value["filterSearch"]);
      console.log(this.records)
      this.queryBarcode = this.formBarcode.value["filterSearch"];
    }
    else {
      this.loadRecordsList();
      this.resetBarcode();
    }
  }

  public async searchByName() {
    this.resetFilter();
    this.resetBarcode();
    if (this.formName.value["filterSearch"]) {
      this.records = await this.apiService.getRecordsByName(this.formName.value["filterSearch"]);
      this.queryName = this.formName.value["filterSearch"];
    }
    else {
      this.loadRecordsList();
      this.resetName();
    }
  }

  public inCollection(recId: number) {
    var res = localStorage.getItem('userRecords');
    if (res != "none") {
      var recs = JSON.parse((res == null) ? "" : res);
      for (let i = 0; i < recs.length; i++) {
        if (recId == recs[i]) { return true }
      }
    }
    return false;
  }

  public async addToUserCollection(recordBrief: RecordBrief) {
    if (! this.authService.isLoggedIn()) {
      this.router.navigate(["/login"]);
    }
    const record = await this.apiService.addToUserCollectionRecord(recordBrief);
    if (record != null) {
      const id = record.id;
      await this.apiService.getUserRecordIds();
      this.router.navigate(['/user-record/' + id]);
    }
  }

  private fromRecordToBrief(records: Record[]) {
    var res_records = [];
    for (let i = 0; i < records.length; i++) {
      var recordBrief = {
        id: records[i].id,
        title: records[i].name,
        year: records[i].year,
        genre: records[i].genre.name,
        coverUrl: records[i].covers.sort((el1, el2) => el1.id - el2.id)[0].picture,
        sourceUID: null,
        barcode: records[i].barcode
      };
      res_records.push(recordBrief);
    }
    return res_records;
  }

}
