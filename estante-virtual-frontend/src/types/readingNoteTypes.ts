import type { ReadingNoteStatus } from "../enums/readingNoteStatus";
import type { BookResponse } from "./bookTypes";
import type { UserResponse } from "./userTypes";

export interface ReadingNoteRequest {
  bookId: number;
  page: number;
  note: string;
}

export interface ReadingNoteAtualizarRequest {
  page: number;
  note: string;
}

export interface ReadingNoteResponse {
  id: number;
  user: UserResponse;
  book: BookResponse;
  page: number;
  note: string;
  status: ReadingNoteStatus;
}