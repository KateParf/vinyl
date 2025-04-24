import { Group } from "./group";

export interface Performer {
    id: number;
    name: string;
    picture: string;
    group?: Group;
}
