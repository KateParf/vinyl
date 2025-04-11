import { Cover } from "./cover";
import { Group } from "./group";
import { Performer } from "./performer";
import { Track } from "./track";

export interface Record {
    id: number;
    name: string;
    year: number;
    publisher: string;
    barcode: string;
    tracks: Track[];
    covers: Cover[];
    performers: Performer[];
    groups: Group[];
}

