import type { BookReadingStatus } from "../enums/bookReadingStatus";
import type { UserBookAtualizarRequest, UserBookRequest, UserBookResponse } from "../types/userBookTypes";
import ApiManager from "./apiManager";

const UserBookService = {
  viewBookshelf: async (): Promise<UserBookResponse[]> => {
    const response = await ApiManager.get<UserBookResponse[]>('/userBook/listarEstante');
    return response.data;
  },

  viewBookInShelf: async (bookId: number): Promise<UserBookResponse> => {
    const response = await ApiManager.get<UserBookResponse>(`/userBook/listarEstante/${bookId}`);
    return response.data;
  },

  addInShelf: async (data: UserBookRequest): Promise<UserBookResponse> => {
    const response = await ApiManager.post<UserBookResponse>('/userBook/adicionarNaEstante', data);
    return response.data;
  },

  updateMyBook: async (data: UserBookAtualizarRequest, bookId: number): Promise<UserBookResponse> => {
    const response = await ApiManager.patch<UserBookResponse>(`/userBook/atualizarDados/${bookId}`, data);
    return response.data;
  },

  updateRead: async (bookId: number, status: BookReadingStatus): Promise<UserBookResponse> => {
    const response = await ApiManager.patch<UserBookResponse>(
      `/userBook/atualizarLeitura/${bookId}`,
      null,
      {
        params: { readingStatus: status }
      }
    );
    return response.data;
  },

  removeBookFromShelf: async (bookId: number): Promise<void> => {
    await ApiManager.delete<void>(`/userBook/removerDaEstante/${bookId}`);
  },
}

export default UserBookService;
