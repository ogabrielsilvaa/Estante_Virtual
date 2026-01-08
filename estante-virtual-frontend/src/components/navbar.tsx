import React, { useCallback, useState } from "react";
import { FaSearch } from "react-icons/fa";
import type { BookRequest } from "../types/bookTypes";
import BookService from "../services/bookService";
import { Link, useNavigate } from "react-router-dom";

export function Navbar() {
  const navigate = useNavigate();

  const [searchBooks, setSearchBooks] = useState<BookRequest[]>([]);
  const [showResults, setShowResults] = useState(false);

  const [typingTimeout, setTypingTimeout] = useState<any>(null);
  const [loading, setLoading] = useState(false);
  const [hasSearched, setHasSearched] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");

  function handleInputChange(e: React.ChangeEvent<HTMLInputElement>) {
    const text = e.target.value;
    setSearchTerm(text);

    if (typingTimeout) clearTimeout(typingTimeout);

    if (text.length === 0) {
      setSearchBooks([]);
      setShowResults(false);
      return;
    }

    const newTimeout = setTimeout(() => {
      handleSearch(text);
    }, 800);

    setTypingTimeout(newTimeout);
  }

  const handleSearch = useCallback(async (query: string) => {
    if (!query) {
      setSearchBooks([]);
      setHasSearched(false);
      return;
    }

    setLoading(true);
    setHasSearched(true);

    try {
      const results = await BookService.searchBook(query);
      setSearchBooks(results);
    } catch (error) {
      console.error(error);
      alert("Erro ao buscar livros.");
    } finally {
      setLoading(false);
    }
  }, []);

  const getCover = (url: string) => {
    return url ? url : "https://placehold.co/128x190?text=Sem+Capa";
  };

  return (
    <>
      <nav className="bg-background py-4 shadow-md w-full">
        <div className="w-full px-6 md:px-12 flex flex-col md:flex-row items-center justify-between gap-6 md:gap-0">

          <div className="text-center md:text-left">
            <a className="text-primary font-playfair font-medium text-[44px] block leading-tight cursor-pointer">
              Estante Virtual
            </a>

            <h2 className="text-[#A8A599] text-[18px]">
              Seu santuário de leitura pessoal
            </h2>
          </div>

          <div className="flex flex-col md:flex-row items-center gap-6 w-full md:w-auto">
            <div className="flex gap-6">
              <Link to="/myShelf" className="no-underline">
                <h1 className="text-primary font-playfair text-lg font-bold hover:text-amber-300 transition-colors">
                  Minha Estante
                </h1>
              </Link>

              <Link to="/reviews" className="no-underline">
                <h1 className="text-primary font-playfair text-lg font-bold hover:text-amber-300 transition-colors">
                  Reviews
                </h1>
              </Link>
            </div>

            <form className="flex w-full md:w-auto justify-center">
              <div className="relative">
                <FaSearch className="absolute left-4 top-1/2 -translate-y-1/2 text-[#1A1A1F] z-10 pointer-events-none" />

                <input
                  className="w-[180px] h-[50px] bg-primary hover:bg-amber-300 text-white rounded-md pl-12 placeholder-[#1A1A1F] outline-none focus:bg-[#4a4a4a] transition-colors duration-300"
                  type="search"
                  placeholder="Procurar Livros"
                  aria-label="Search"
                  onChange={handleInputChange}
                />
              </div>
            </form>
          </div>

        </div>
      </nav>

      {loading && (
        <div className="flex justify-center py-20">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary"></div>
        </div>
      )}

      {!loading && hasSearched && (
        <div>
          <div className="flex justify-between items-center mb-6 px-6 md:px-12">
            <h1 className="text-3xl font-bold text-primary font-playfair">
              {searchBooks.length > 0 ? "Resultados da Pesquisa" : "Não há resultados"}
            </h1>
          </div>

          <div className="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-5 gap-6 px-6 md:px-12 mt-8 mb-12">
            {searchBooks.map((book, index) => (
              <div
                key={book.isbn || index}
                onClick={() => navigate("/bookDetails", { state: book })}
                className="bg-white rounded-lg shadow-lg overflow-hidden hover:scale-105 transition-transform duration-300 cursor-pointer flex flex-col h-full group"
              >
                <div className="aspect-[2/3] overflow-hidden bg-gray-200 relative">
                  <img
                    src={getCover(book.coverUrl)}
                    alt={book.title}
                    className="h-full w-full object-cover group-hover:opacity-90 transition-opacity"
                  />
                </div>
                <div className="p-4 flex flex-col flex-1">
                  <h3
                    className="font-bold text-gray-800 text-lg leading-tight mb-1 line-clamp-2"
                    title={book.title}
                  >
                    {book.title}
                  </h3>
                  <p className="text-sm text-gray-600 mb-2 font-medium">
                    {book.author}
                  </p>
                  <span className="text-xs text-primary mt-auto">
                    Novo - Clique para adicionar
                  </span>
                </div>
              </div>
            ))}
            {searchBooks.length === 0 && (
              <div className="col-span-full text-center py-10 text-gray-400">
                Nenhum livro encontrado na biblioteca externa.
              </div>
            )}
          </div>
        </div>
      )}
    </>
  );
}
