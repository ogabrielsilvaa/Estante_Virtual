import type { RatingLevel } from "../enums/ratingLevel";
import type { ReviewStatus } from "../enums/reviewStatus";
import type { BookResponse } from "./bookTypes";
import type { UserResponse } from "./userTypes";

export interface ReviewRequest {
  bookId: number;
  ratingPlot: RatingLevel;
  ratingCharacters: RatingLevel;
  ratingWriting: RatingLevel;
  ratingImmersion: RatingLevel;
  text: string;
  status: ReviewStatus;
}

export interface ReviewAtualizarRequest {
  ratingPlot: RatingLevel;
  ratingCharacters: RatingLevel;
  ratingWriting: RatingLevel;
  ratingImmersion: RatingLevel;
  text: string;
  status: ReviewStatus;
}

export interface ReviewResponse {
  id: number;
  user: UserResponse;
  book: BookResponse;
  ratingPlot: RatingLevel;
  ratingCharacters: RatingLevel;
  ratingWriting: RatingLevel;
  ratingImmersion: RatingLevel;
  text: string;
  status: ReviewStatus;
  createdAt: string;
}