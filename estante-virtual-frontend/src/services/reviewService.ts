import type { ReviewStatus } from "../enums/reviewStatus";
import type { Page } from "../types/commonTypes";
import type { ReviewAtualizarRequest, ReviewRequest, ReviewResponse } from "../types/reviewTypes";
import ApiManager from "./apiManager";

const ReviewService = {
  viewFeed: async (
    page: number=0,
    size: number=10
  ): Promise<Page<ReviewResponse>> => {
    const response = await ApiManager.get<Page<ReviewResponse>>(`/reviews/feed`, {
      params: {
        page: page,
        size: size,
        sort: 'createdAt,DESC',
      }
    })

    return response.data;
  },

  viewReviews: async (
    status?: ReviewStatus,
    page: number = 0,
    size: number = 10
  ): Promise<Page<ReviewResponse>> => {
    const response = await ApiManager.get<Page<ReviewResponse>>(`/reviews/listarReviews`, {
      params: {
        status: status,
        page: page,
        size: size,
        sort: 'createdAt,DESC',
      }
    });

    return response.data;
  },

  viewReview: async (reviewId: number): Promise<ReviewResponse> => {
    const response = await ApiManager.get<ReviewResponse>(`/reviews/listarReviewPorId/${reviewId}`);
    return response.data;
  },

  reviewRegister: async (data: ReviewRequest): Promise<ReviewResponse> => {
    const response = await ApiManager.post<ReviewResponse>(`/reviews/cadastrar`, data);
    return response.data;
  },

  updateReview: async (data: ReviewAtualizarRequest, reviewId: number): Promise<ReviewResponse> => {
    const response = await ApiManager.patch<ReviewResponse>(`/reviews/atualizar/${reviewId}`, data);
    return response.data;
  },

  deleteReview: async (reviewId: number): Promise<void> => {
    await ApiManager.delete<void>(`/reviews/deletar/${reviewId}`);
  },
};

export default ReviewService;
