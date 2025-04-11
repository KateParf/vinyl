import { Group } from "./group";

export interface Performer {
    id: number;
    name: string;
    image: string;
    groups?: Group[];
}
