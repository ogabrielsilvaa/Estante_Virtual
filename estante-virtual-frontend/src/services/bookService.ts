import type { BookAtualizarRequest, BookRequest, BookResponse } from "../types/bookTypes";
import type { Page } from "../types/commonTypes";
import ApiManager from "./apiManager";

const BookService = {
  viewCatalogue: async (page = 0, size = 10): Promise<Page<BookResponse>> => {
    const response = await ApiManager.get<Page<BookResponse>>('/books/listarCatalogo', {
      params: { page, size, sort: 'title' }
    });
    return response.data;
  },

  viewBook: async(bookId: number): Promise<BookResponse> => {
    const response = await ApiManager.get<BookResponse>(`/books/listarLivroPorId/${bookId}`);
    return response.data;
  },

  bookRegister: async (data: BookRequest): Promise<BookResponse> => {
    const response = await ApiManager.post<BookResponse>('/books/cadastrar', data);
    return response.data;
  },

  updateBook: async (bookId: number, data: BookAtualizarRequest): Promise<BookResponse> => {
    const response = await ApiManager.put<BookResponse>(`/books/atualizar/${bookId}`, data);
    return response.data;
  },

  changeStatus: async (bookId: number, status: boolean): Promise<void> => {
    await ApiManager.patch<void>(`/books/mudarStatus/${bookId}`, status);
  },

  searchBook: async(query: string): Promise<BookRequest[]> => {
    if (!query) return [];

    const response = await ApiManager.get<BookRequest[]>('/books/external-search', {
      params: {query: query}
    });
    return response.data;
  },
};

export default BookService;
