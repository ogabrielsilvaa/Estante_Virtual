import type { BookReadingStatus } from "../enums/bookReadingStatus";
import type { BookResponse } from "./bookTypes";

export interface UserBookRequest {
  bookId: number;
  readingStatus: BookReadingStatus;
  pagesRead: number;
  startDate: string;
  finishDate: string;
  favorite: string;
}

export interface UserBookAtualizarRequest {
  pagesRead: number;
  startDate: string;
  finishDate: string;
  favorite: string;
}

export interface UserBookResponse {
  id: number;
  book: BookResponse;
  readingStatus: BookReadingStatus;
  pagesRead: number;
  startDate: string;
  finishDate: string;
  favorite: string;
  statusActive: string;
}