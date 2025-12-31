import { ReadingNoteStatus } from "../enums/readingNoteStatus";
import type { Page } from "../types/commonTypes";
import type { ReadingNoteAtualizarRequest, ReadingNoteRequest, ReadingNoteResponse } from "../types/readingNoteTypes";
import ApiManager from "./apiManager";

const ReadingNoteService = {
  viewNotes: async (
    status?: ReadingNoteStatus,
    page: number = 0,
    size: number = 10
  ): Promise<Page<ReadingNoteResponse>> => {
      const response = await ApiManager.get<Page<ReadingNoteResponse>>(`/readingNotes/listarNotas`, {
        params: {
          status: status,
          page: page,
          size: size,
          sort: 'createdAt,DESC',
        }
      });

      return response.data;
    },

    viewNote: async(noteId: number): Promise<ReadingNoteResponse> => {
      const response = await ApiManager.get<ReadingNoteResponse>(`/readingNotes/listarNotaPorId/${noteId}`);
      return response.data;
    },

    noteRegister: async(data: ReadingNoteRequest): Promise<ReadingNoteResponse> => {
      const response = await ApiManager.post<ReadingNoteResponse>('/readingNotes/cadastrar', data);
      return response.data;
    },

    updateNote: async(data: ReadingNoteAtualizarRequest, noteId: number): Promise<ReadingNoteResponse> => {
      const response = await ApiManager.patch<ReadingNoteResponse>(`/readingNotes/atualizar/${noteId}`, data);
      return response.data;
    },

    deleteNote: async(noteId: number): Promise<void> => {
      await ApiManager.delete<void>(`/readingNotes/deletar/${noteId}`);
    },
};

export default ReadingNoteService;
