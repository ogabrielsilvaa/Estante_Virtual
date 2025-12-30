export interface BookRequest {
  title: string;
  author: string;
  isbn: string;
  coverUrl: string;
  synopsis: string;
  pageCount: number;
  publisher: string;
  publicationYear: number;
}

export interface BookAtualizarRequest {
  title: string;
  author: string;
  isbn: string;
  coverUrl: string;
  synopsis: string;
  pageCount: number;
  publisher: string;
  publicationYear: number;
}

export interface BookResponse {
  id: number;
  title: string;
  author: string;
  isbn: string;
  coverUrl: string;
  synopsis: string;
  pageCount: number;
  publisher: string;
  publicationYear: number;
  statusActive: string;
}