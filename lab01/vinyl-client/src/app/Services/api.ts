import { Inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RecordBrief } from '../models/recordBrief';
import { Performer } from '../models/performer';
import { Group } from '../models/group';
import { Genre } from '../models/genre';
import { Record } from '../models/record';
import { PersonalRecord } from '../models/personalRecord';
import { User } from '../models/user';


@Injectable({ providedIn: 'root' })
export class APIService {

  private baseUrl: string = "";

  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string) {
    this.baseUrl = baseUrl;
  }

  //---- genres

  public async getGenresList(): Promise<Genre[]> {

    return await this.http.get<Genre[]>(`${this.baseUrl}api/genres`).toPromise().catch(
      error => console.error("getGenresList error: ", error)
    ) ?? [];
  }

  //---- groups 

  public async getGroupsList(): Promise<Group[]> {
    return await this.http.get<Group[]>(`${this.baseUrl}api/groups/list`).toPromise().catch(
      error => console.error("getGroupsList error: ", error)
    ) ?? [];
  }

  public async getGroupById(groupId: number): Promise<Group | null> {
    return await this.http.get<Group>(`${this.baseUrl}api/groups/get/${groupId}`).toPromise().catch(
      error => console.error("getGroupById error: ", error)
    ) ?? null;
  }

  public async getGroupRecords(groupId: number): Promise<RecordBrief[]> {
    return await this.http.get<RecordBrief[]>(`${this.baseUrl}api/groups/records/${groupId}`).toPromise().catch(
      error => console.error("getGroupRecords error: ", error)
    ) ?? [];
  }

  //---- performers

  public async getPerformersList(): Promise<Performer[]> {
    return await this.http.get<Performer[]>(`${this.baseUrl}api/performers/list`).toPromise().catch(
      error => console.error("getPerformersList error: ", error)
    ) ?? [];
  }

  public async getPerformerById(performerId: number): Promise<Performer | null> {
    return await this.http.get<Performer>(`${this.baseUrl}api/performers/get/${performerId}`).toPromise().catch(
      error => console.error("getPerformerById error: ", error)
    ) ?? null;
  }

  public async getPerformerRecords(performerId: number): Promise<RecordBrief[]> {
    return await this.http.get<RecordBrief[]>(`${this.baseUrl}api/performers/records/${performerId}`).toPromise().catch(
      error => console.error("getPerformerRecords error: ", error)
    ) ?? [];
  }

  //---- records 

  public async getRecordsList(): Promise<RecordBrief[]> {
    return await this.http.get<RecordBrief[]>(`${this.baseUrl}api/records/list`).toPromise().catch(
      error => console.error("getRecordsList error: ", error)
    ) ?? [];
  }

  public async getRecordsWithFilters(genre_id?: number | null, group_id?: number | null, performer_id?: number | null, decade?: number | null): Promise<RecordBrief[]> {
    let filterParams = "?";

    if (genre_id) filterParams += "&genre_id=" + genre_id;
    if (group_id) filterParams += "&group_id=" + group_id;
    if (performer_id) filterParams += "&performer_id=" + performer_id;
    if (decade) filterParams += "&decade=" + decade;

    return await this.http.get<RecordBrief[]>(`${this.baseUrl}api/records/list${filterParams}`).toPromise().catch(
      error => console.error("getRecordsWithFilters error: ", error)
    ) ?? [];
  }

  public async getRecordsByBarcode(barcode: string): Promise<RecordBrief[]> {
    return await this.http.post<RecordBrief[]>(`${this.baseUrl}api/records/search/barcode`, barcode).toPromise().catch(
      error => console.error("getRecordsByBarcode error: ", error)
    ) ?? [];
  }

  public async getRecordsByName(name: string): Promise<RecordBrief[]> {
    return await this.http.post<RecordBrief[]>(`${this.baseUrl}api/records/search/name`, name).toPromise().catch(
      error => console.error("getRecordsByName error: ", error)
    ) ?? [];
  }

  public async getRecord(recordId: number): Promise<Record | null> {
    return await this.http.get<Record>(`${this.baseUrl}api/records/get/${recordId}`).toPromise().catch(
      error => console.error("getRecordsList error: ", error)
    ) ?? null;
  }

  //---- tracks

  public async getTrackURL(recordId: number, trackId: number): Promise<string> {
    var res = await this.http.get<any>(`${this.baseUrl}api/records/${recordId}/play/${trackId}`).toPromise().catch(
      error => console.error("getTrackURL error: ", error));
    return res.trackName;
  }

  //---- personal records

  public async getPersonalRecordsList(): Promise<RecordBrief[]> {
    return await this.http.get<RecordBrief[]>(`${this.baseUrl}api/userrecords/list`).toPromise().catch(
      error => console.error("getPersonalRecordsList error: ", error)
    ) ?? [];
  }

  public async getUserRecordIds() {
    var res = await this.http.get<Number[]>(`${this.baseUrl}api/userrecords/list/ids`).toPromise().catch(
      error => console.error("getUserRecordIds error: ", error)
    ) ?? [];
    localStorage.setItem('userRecords', JSON.stringify(res));
  }

  public async getPersonalRecordById(personalRecordId: number): Promise<PersonalRecord | null> {
    return await this.http.get<PersonalRecord>(`${this.baseUrl}api/userrecords/get/${personalRecordId}`).toPromise().catch(
      error => console.error("getPersonalRecordById error: ", error)
    ) ?? null;
  }

  public async addToUserCollectionRecord(record: RecordBrief): Promise<PersonalRecord | null> {
    return await this.http.post<PersonalRecord>(`${this.baseUrl}api/userrecords/addbrief`, record).toPromise().catch(
      error => console.error("addToUserCollectionRecord error: ", error)
    ) ?? null;
  }

  public async addToUserCollectionRecordId(recordId: number): Promise<PersonalRecord | null> {
    return await this.http.get<PersonalRecord>(`${this.baseUrl}api/userrecords/add/${recordId}`).toPromise().catch(
      error => console.error("addToUserCollectionRecordId error: ", error)
    ) ?? null;
  }

  public async editUserCollectionRecord(record: PersonalRecord): Promise<PersonalRecord | null> {
    var dto = { id: record.record.id, condition: record.condition, comment: record.comment };
    return await this.http.post<PersonalRecord>(`${this.baseUrl}api/userrecords/edit`, dto).toPromise().catch(
      error => console.error("editUserCollectionRecord error: ", error)
    ) ?? null;
  }

  public async deleteFromUserCollection(personalRecordId: number): Promise<void> {
    await this.http.get<void>(`${this.baseUrl}api/userrecords/delete/${personalRecordId}`).toPromise().catch(
      error => console.error("deleteFromUserCollection error: ", error)
    );
  }

  //---- user ----

  public async registration(email: string, login: string, password: string): Promise<string | null> {
    //!! TODO
    // testdata
    if (login == "katya") {
      // писать в локалстораж флаг
      localStorage.setItem('auth', 'true');
      return null;
    } else {
      // чистить локалсторож
      localStorage.removeItem('auth');
      return "AUTH ERROR!"
    }
  }

  public async getUserInfo(): Promise<User | null> {
    // testdata
    /*
    var user = { name: "average vinyl lover", password: "123", email: "test@email.com" };
    return user;
    */
    return await this.http.get<User>(`${this.baseUrl}api/userinfo`).toPromise().catch(
      error => console.error("getUserInfo error: ", error)
    ) ?? null;

  }

  public async changePassword(oldPassword: string, newPassword: string): Promise<any> {
    /*this.http.post<any>("/api/password_change", newPassword)
      .subscribe(res => {
        alert('PASSWORD CHANGED SUCCESFUL');
      }, err => {
        alert("Something went wrong")
      })*/
    const dto = {"oldPassword": oldPassword, "newPassword": newPassword}; 
    const res = await this.http.post<any>(`${this.baseUrl}api/password_change`, dto).toPromise().catch(
      error => console.error("getUserInfo error: ", error)
    ) ?? { status: -1, message: "Ошибка смены пароля"};
    return res;
  }

}
