export interface RecordBrief {
    id: number|null;
    title: string;
    year: number;
    genre: string;
    coverUrl: string;
    sourceUID: string|null;
    barcode: string|null;
}