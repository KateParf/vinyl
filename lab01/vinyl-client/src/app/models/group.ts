import { Performer } from './performer';
export interface Group {
    id: number;
    name: string;
    picture: string;
    performer?: Performer;
}