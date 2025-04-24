import { Cover } from "./cover";
import { Genre } from "./genre";
import { Group } from "./group";
import { Performer } from "./performer";
import { Track } from "./track";

export interface Record {
    id: number;
    name: string;
    year: number;
    genre: Genre;
    publisher: string;
    barcode: string;
    tracks: Track[];
    covers: Cover[];
    performers: Performer[];
    groups: Group[];
}

