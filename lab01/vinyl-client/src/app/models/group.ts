import { Performer } from './performer';
export interface Group {
    id: number;
    name: string;
    image: string;
    performers?: Performer[];
}