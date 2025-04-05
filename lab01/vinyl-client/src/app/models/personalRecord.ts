import { Record } from "./record";

export interface PersonalRecord {
    id: number;
    record: Record;
    condition: string;
    comment: string;
}

