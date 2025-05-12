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

  // --- для трека
  currentAudio: HTMLAudioElement | null = null;
  currentTrackId: number | null = null;
  isPlaying = false;
  currentTime = 0;
  duration = 0;

  constructor(private route: ActivatedRoute, private router: Router, private apiService: APIService) {
    void route.params.subscribe(params => this.recordId = params["id"]);
    this.loadRecordData();
  }

  private async loadRecordData() {
    var record = await this.apiService.getRecord(this.recordId);
    if (record) {
      this.record = record;
      this.recordName = this.record.name;
      this.recordYear = this.record.year;
      this.recordPublisher = this.record.publisher;
      this.recordBarcode = this.record.barcode;
      this.recordTracks = this.record.tracks.sort((el1,el2) => el1.id - el2.id);
      this.recordCovers = this.record.covers.sort((el1,el2) => el1.id - el2.id);
      this.recordPerformers = this.record.performers;
      this.recordGroups = this.record.groups;
    }
  }

  // добавить в коллекцию юзера
  public async addToUserCollection(recordId: number) {
    var record = await this.apiService.addToUserCollectionRecordId(recordId);
    if (record != null) {
      const id = record.id;
      await this.apiService.getUserRecordIds();
      this.router.navigate(['/user-record/' + id]);
    }
  }

  public inCollection(recId: number) {
    var res = localStorage.getItem('userRecords');
    var recs = JSON.parse(res == null ? "" : res);
    for (let i = 0; i < recs.length; i++) {
      if (recId == recs[i]) { return true }
    }
    return false;
  }

  // получить и играть трек
  public async playTrack(trackId: number) {
    // Если это тот же трек, что уже играет
    if (this.currentTrackId === trackId && this.currentAudio) {
      if (this.isPlaying) {
        this.currentAudio.pause();
        this.isPlaying = false;
      } else {
        await this.currentAudio.play();
        this.isPlaying = true;
      }
      return;
    }

    // Если это новый трек
    if (this.currentAudio) {
      this.currentAudio.pause();
      this.currentAudio = null;
    }

    const res = await this.apiService.getTrackURL(this.recordId, trackId);
    this.currentAudio = new Audio(res);
    this.currentTrackId = trackId;

    // Установка обработчиков
    this.currentAudio.addEventListener('timeupdate', () => {
      this.currentTime = this.currentAudio?.currentTime || 0;
    });

    this.currentAudio.addEventListener('loadedmetadata', () => {
      this.duration = this.currentAudio?.duration || 0;
    });

    this.currentAudio.addEventListener('ended', () => {
      this.isPlaying = false;
      this.currentTime = 0;
    });

    try {
      await this.currentAudio.play();
      this.isPlaying = true;
    } catch (error) {
      console.error('Ошибка воспроизведения:', error);
    }
  }

  // -- работа с временем трека
  formatTime(seconds: number): string {
    if (isNaN(seconds)) return '0:00';

    const minutes = Math.floor(seconds / 60);
    const secs = Math.floor(seconds % 60);
    return `${minutes}:${secs < 10 ? '0' : ''}${secs}`;
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
