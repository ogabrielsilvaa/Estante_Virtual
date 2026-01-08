import { Navbar } from "../components/navbar";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/authContext";
import { useEffect, useState } from "react";
import type { UserBookResponse } from "../types/userBookTypes";
import UserBookService from "../services/userBookService";
import { BookCard } from "../components/bookCard";

export function MyShelf() {
  const navigate = useNavigate();
  const { signOut } = useAuth();

  const [myBooks, setMyBooks] = useState<UserBookResponse[]>([]);

  const [loading, setLoading] = useState(false);

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

  async function handleLogOut() {
    signOut();
    navigate("/login");
  }

  async function handleDelete(e: React.MouseEvent, userBookId: number) {
    e.stopPropagation();

    if (!window.confirm("Tem certeza que deseja remover este livro ?")) return;

    setLoading(true);
    try {
      await UserBookService.removeBookFromShelf(userBookId);

      setMyBooks((currentBooks) =>
        currentBooks.filter((book) => book.id !== userBookId)
      );
    } catch (error) {
      console.error("Erro ao tentar apagar o livro da estante: ", error);
      alert("Erro ao remover o livro.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="min-h-screen bg-dark pb-10">
      <Navbar />

      <div className="w-full max-w-[1200px] mx-auto px-6 mt-8">
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-3xl font-bold text-primary font-playfair">
            {myBooks.length > 0 ? "Minha Estante" : "Sua estante está vazia"}
          </h1>

          <button
            type="button"
            onClick={handleLogOut}
            className="w-40 py-3 text-white text-base bg-red-600 rounded-md hover:bg-red-700 hover:scale-105 transition-all duration-300 shadow-md font-medium focus:outline-none"
          >
            Sair
          </button>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 px-6 md:px-12 mt-8 mb-12">
          {myBooks.map((userBook) => (
            <BookCard
              key={userBook.id}
              id={userBook.id}
              title={userBook.book.title}
              author={userBook.book.author}
              coverUrl={userBook.book.coverUrl}
              readingStatus={userBook.readingStatus}
              pagesRead={userBook.pagesRead}
              pageCount={userBook.book.pageCount}
              isFavorite={userBook.favorite === "S" || userBook.favorite === true}
              onClick={() => navigate("/bookDetails", { state: {...userBook.book, ...userBook } })}
              onDelete={(e) => handleDelete(e, userBook.id)}
            />
          ))}

          {myBooks.length === 0 && (
            <div className="col-span-full text-center py-20 text-gray-500">
              <p className="text-xl mb-2">Sua estante está vazia.</p>
              <p className="text-sm">
                Use a barra de pesquisa acima para encontrar e adicionar novos
                livros!
              </p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
