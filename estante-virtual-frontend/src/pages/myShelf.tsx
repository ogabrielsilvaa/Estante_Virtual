import { Navbar } from "../components/navbar";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/authContext";
import { useCallback, useState } from "react";
import { type BookRequest } from "../types/bookTypes";
import BookService from "../services/bookService";

export function MyShelf() {
  const navigate = useNavigate();
  const { signOut } = useAuth();

  const [books, setBooks] = useState<BookRequest[]>([]);
  const [loading, setLoading] = useState(false);
  const [hasSearched, setHasSearched] = useState(false);

  const handleSearch = useCallback( async(query: string) => {
    if (!query) {
      setBooks([]);
      setHasSearched(false);
      return;
    }

    setLoading(true);
    setHasSearched(true);

    try {
      const results = await BookService.searchBook(query);
      setBooks(results);
    } catch (error) {
      console.error(error);
      alert("Erro ao buscar livros.");
    } finally {
      setLoading(false);
    }
  }, []);

  async function handleLogOut() {
    signOut();
    navigate("/login");
  }

  const getCover = (url: string) => {
    return url ? url : "https://placehold.co/128x190?text=Sem+Capa";
  };

  return (
    <div className="min-h-screen bg-dark pb-10">
      <Navbar onSearch={handleSearch} />

      <div className="w-full max-w-[1200px] mx-auto px-6 mt-8">
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-3xl font-bold text-primary font-playfair">
            {hasSearched ? "Resultados da Pesquisa" : "Minha Estante"}
          </h1>

          <button
            type="button"
            onClick={handleLogOut}
            className="w-40 py-3 text-white text-base bg-red-600 rounded-md hover:bg-red-700 hover:scale-105 transition-all duration-300 shadow-md font-medium focus:outline-none"
          >
            Sair
          </button>
        </div>

        {loading && (
           <div className="flex justify-center py-20">
             <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary"></div>
           </div>
        )}

        {!loading && books.length > 0 && (
          <div className="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-5 gap-6">
            {books.map((book, index) => (
              <div
                key={book.isbn || index}
                className="bg-white rounded-lg shadow-lg overflow-hidden hover:scale-105 transition-transform duration-300 cursor-pointer flex flex-col h-full group"
              >
                <div className="h-[280px] overflow-hidden bg-gray-200 relative">
                  <img
                    src={getCover(book.coverUrl)}
                    alt={book.title}
                    className="h-full w-full object-cover group-hover:opacity-90 transition-opacity"
                  />
                  {book.publicationYear && (
                    <span className="absolute bottom-2 right-2 bg-black bg-opacity-70 text-white text-xs px-2 py-1 rounded">
                      {book.publicationYear}
                    </span>
                  )}
                </div>

                <div className="p-4 flex flex-col flex-1">
                  <h3 className="font-bold text-gray-800 text-lg leading-tight mb-1 line-clamp-2" title={book.title}>
                    {book.title}
                  </h3>

                  <p className="text-sm text-gray-600 mb-2 font-medium">
                    {book.author || "Autor desconhecido"}
                  </p>

                  <span className="text-xs text-primary mt-auto">
                    {book.publisher}
                  </span>
                </div>
              </div>
            ))}
          </div>
        )}

        {!loading && hasSearched && books.length === 0 && (
          <div className="text-center py-20 text-gray-500">
            <p className="text-xl">Nenhum livro encontrado para essa pesquisa.</p>
          </div>
        )}

        {!hasSearched && (
          <div className="text-center py-20 text-gray-400">
            <p>Digite o nome de um livro na barra acima para começar.</p>
          </div>
        )}
      </div>
    </div>
  );
}
