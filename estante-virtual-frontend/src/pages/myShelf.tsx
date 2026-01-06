import { Navbar } from "../components/navbar";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/authContext";
import { useCallback, useEffect, useState } from "react";
import { type BookRequest } from "../types/bookTypes";
import BookService from "../services/bookService";
import type { UserBookResponse } from "../types/userBookTypes";
import UserBookService from "../services/userBookService";
import { FaBookmark, FaBookReader, FaCheckCircle, FaHeart, FaRegTimesCircle, FaClock } from "react-icons/fa";

export function MyShelf() {
  const navigate = useNavigate();
  const { signOut } = useAuth();

  const [searchBooks, setSearchBooks] = useState<BookRequest[]>([]);
  const [myBooks, setMyBooks] = useState<UserBookResponse[]>([]);

  const [loading, setLoading] = useState(false);
  const [hasSearched, setHasSearched] = useState(false);

  useEffect(() => {
    fetchMyShelf();
  }, []);

  async function fetchMyShelf() {
    setLoading(true);
    try {
      const data = await UserBookService.viewBookshelf();
      setMyBooks(data);
    } catch (error) {
      console.error("Erro ao carregar estante:", error);
    } finally {
      setLoading(false);
    }
  }

  const handleSearch = useCallback( async(query: string) => {
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

  async function handleLogOut() {
    signOut();
    navigate("/login");
  }

  const getCover = (url: string) => {
    return url ? url : "https://placehold.co/128x190?text=Sem+Capa";
  };

  const getStatusBadge = (status: string) => {
    switch (status) {
      case "LIDO": return <span className="bg-green-100 text-green-800 text-xs px-2 py-1 rounded-full flex items-center gap-1"><FaCheckCircle/> Lido</span>;
      case "LENDO": return <span className="bg-blue-100 text-blue-800 text-xs px-2 py-1 rounded-full flex items-center gap-1"><FaBookReader/> Lendo</span>;
      case "QUERO_LER": return <span className="bg-yellow-100 text-yellow-800 text-xs px-2 py-1 rounded-full flex items-center gap-1"><FaBookmark/> Quero Ler</span>;
      case "ABANDONEI": return <span className="bg-red-100 text-red-800 text-xs px-2 py-1 rounded-full flex items-center gap-1"><FaRegTimesCircle/> Abandonei</span>;
      case "PENDENTE": return <span className="bg-gray-200 text-gray-700 text-xs px-2 py-1 rounded-full flex items-center gap-1"><FaClock/> Pendente</span>;
      default: return null;
    }
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

        {!loading && hasSearched && (
          <div className="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-5 gap-6">
            {searchBooks.map((book, index) => (
              <div 
                key={book.isbn || index} 
                onClick={() => navigate('/bookDetails', { state: book })}
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
                  <h3 className="font-bold text-gray-800 text-lg leading-tight mb-1 line-clamp-2" title={book.title}>{book.title}</h3>
                  <p className="text-sm text-gray-600 mb-2 font-medium">{book.author}</p>
                  <span className="text-xs text-primary mt-auto">Novo - Clique para adicionar</span>
                </div>
              </div>
            ))}
            {searchBooks.length === 0 && (
                <div className="col-span-full text-center py-10 text-gray-400">Nenhum livro encontrado na biblioteca externa.</div>
            )}
          </div>
        )}

        {!loading && !hasSearched && (
           <div className="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-5 gap-6">
             {myBooks.map((userBook) => (
               <div 
                 key={userBook.id}
                 className="bg-white rounded-lg shadow-lg overflow-hidden hover:scale-105 transition-transform duration-300 cursor-pointer flex flex-col h-full relative"
               >
                 {(userBook.favorite === 'S' || userBook.favorite === true) && (
                    <div className="absolute top-2 right-2 z-10 text-red-500 bg-white rounded-full p-1 shadow-md">
                        <FaHeart />
                    </div>
                 )}
                 <div className="aspect-[2/3] overflow-hidden bg-gray-200 relative">
                    <img 
                        src={getCover(userBook.book.coverUrl)} 
                        alt={userBook.book.title} 
                        className="h-full w-full object-cover group-hover:opacity-90 transition-opacity" 
                    />
                 </div>

                 <div className="p-4 flex flex-col flex-1">
                   <div className="mb-2">
                       {getStatusBadge(userBook.readingStatus)}
                   </div>

                   <h3 className="font-bold text-gray-800 text-lg leading-tight mb-1 line-clamp-2">
                       {userBook.book.title}
                   </h3>
                   <p className="text-sm text-gray-600 font-medium">
                       {userBook.book.author}
                   </p>
                   
                   {userBook.readingStatus === 'LENDO' && userBook.book.pageCount > 0 && (
                       <div className="w-full bg-gray-200 rounded-full h-1.5 mt-3">
                           <div 
                               className="bg-blue-600 h-1.5 rounded-full" 
                               style={{ width: `${(userBook.pagesRead / userBook.book.pageCount) * 100}%` }}
                           ></div>
                       </div>
                   )}
                 </div>
               </div>
             ))}

             {myBooks.length === 0 && (
                <div className="col-span-full text-center py-20 text-gray-500">
                    <p className="text-xl mb-2">Sua estante está vazia.</p>
                    <p className="text-sm">Use a barra de pesquisa acima para encontrar e adicionar novos livros!</p>
                </div>
             )}
           </div>
        )}

      </div>
    </div>
  );
}
