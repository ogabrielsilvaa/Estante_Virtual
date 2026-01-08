import { useLocation, useNavigate } from "react-router-dom";
import { useState } from "react";
import { Navbar } from "../components/navbar";
import {
  FaArrowLeft,
  FaBookOpen,
  FaBuilding,
  FaCalendar,
  FaHeart,
  FaRegHeart,
} from "react-icons/fa";
import UserBookService from "../services/userBookService";
import { BookReadingStatus } from "../enums/bookReadingStatus";
import type { BookDetailsState } from "../types/userBookTypes";

export function BookDetails() {
  const navigate = useNavigate();
  const location = useLocation();

  const book = location.state as BookDetailsState;

  const [loading, setLoading] = useState(false);

  const [status, setStatus] = useState<BookReadingStatus>(
     book.readingStatus || BookReadingStatus.QUERO_LER
  );
  const [pagesRead, setPagesRead] = useState(book.pagesRead || 0);
  const [startDate, setStartDate] = useState(book.startDate || "");
  const [finishDate, setFinishDate] = useState(book.finishDate || "");
  const [favorite, setFavorite] = useState(
      book.favorite === true || book.favorite === 'S'
  );

  if (!book) {
    return (
      <div className="h-screen bg-dark flex items-center justify-center text-white">
        <p>
          Livro não encontrado.{" "}
          <span
            onClick={() => navigate("/myShelf")}
            className="text-primary cursor-pointer underline"
          >
            Voltar.
          </span>
        </p>
      </div>
    );
  }

  const getCover = (url: string) => {
    return url ? url : "https://placehold.co/128x190?text=Sem+Capa";
  };

  async function handleAddToShelf() {
    setLoading(true);

    const statusMap: Record<string, number> = {
      QUERO_LER: 0,
      LENDO: 1,
      LIDO: 2,
      PENDENTE: 3,
      ABANDONEI: 4,
    };

    try {
      const payload: any = {
        ...book,
        readingStatus: statusMap[status],
        pagesRead: pagesRead,
        startDate: startDate || null,
        finishDate: finishDate || null,
        favorite: favorite,
      };

      if (book.userBookId) {
        await UserBookService.updateMyBook(payload, book.userBookId);
        alert("Dados do livro atualizados com sucesso!");
      } else {
        await UserBookService.addInShelf(payload);
        alert("Livro cadastrado na sua estante com sucesso!");
      }
      
      navigate("/myShelf");
    } catch (error: any) {
      console.error("Erro ao salvar: ", error);

      if (
        error.response?.data?.message?.includes("existe") ||
        error.response?.status === 409
      ) {
        alert("Este livro já existe na base de dados!");
      } else {
        alert("Erro ao salvar o livro. Tente novamente.");
      }
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="min-h-screen bg-dark pb-10">
      <Navbar />

      <div className="max-w-[1000px] mx-auto px-6 mt-10">
        <button
          onClick={() => navigate(-1)}
          className="flex items-center justify-center gap-3 mb-4 w-40 py-3 text-white bg-primary box-border rounded-md hover:bg-[#B89850] hover:scale-105 transition-all duration-300 focus:ring-4 focus:ring-success-medium shadow-xs font-medium leading-5 rounded-base text-sm px-4 py-2.5 focus:outline-none"
        >
          <FaArrowLeft /> Voltar
        </button>

        <div className="bg-white rounded-lg shadow-2xl overflow-hidden flex flex-col md:flex-row">
          <div className="md:w-1/3 bg-gray-200">
            <img
              src={getCover(book.coverUrl)}
              alt={book.title}
              className="w-full h-full object-cover"
            />
          </div>

          <div className="md:w-2/3 p-8 flex flex-col">
            <div className="flex justify-between items-start">
              <div>
                <h1 className="text-4xl font-playfair font-bold text-dark mb-2 leading-tight">
                  {book.title}
                </h1>
                <h2 className="text-xl text-gray-600 font-medium mb-6">
                  por <span className="text-primary">{book.author}</span>
                </h2>
              </div>
              <button
                onClick={() => setFavorite(!favorite)}
                className="text-3xl text-red-500 hover:scale-110 transition-transform"
                title="Favoritar"
              >
                {favorite ? <FaHeart /> : <FaRegHeart />}
              </button>
            </div>

            <div className="grid grid-cols-2 gap-4 mb-6 text-sm text-gray-600 border-b border-gray-200 pb-6">
              <div className="flex items-center gap-2">
                <FaCalendar className="text-primary" />{" "}
                <span>{book.publicationYear || "N/A"}</span>
              </div>
              <div className="flex items-center gap-2">
                <FaBuilding className="text-primary" />{" "}
                <span>{book.publisher || "N/A"}</span>
              </div>
              <div className="flex items-center gap-2">
                <FaBookOpen className="text-primary" />{" "}
                <span>{book.pageCount || "?"} pág.</span>
              </div>
              <div className="flex items-center gap-2">
                <span className="font-bold text-primary">ISBN:</span>{" "}
                <span className="truncate w-24" title={book.isbn}>
                  {book.isbn || "N/A"}
                </span>
              </div>
            </div>

            <div className="bg-gray-50 p-4 rounded-md mb-6">
              <h3 className="font-bold text-dark mb-3">Configurar Leitura</h3>

              <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                <div className="flex flex-col">
                  <label className="text-xs font-bold text-gray-500 mb-1">
                    Status
                  </label>
                  <select
                    value={status}
                    onChange={(e) =>
                      setStatus(e.target.value as BookReadingStatus)
                    }
                    className="h-10 border border-gray-300 rounded px-2 text-sm focus:border-primary focus:outline-none"
                  >
                    <option value="QUERO_LER">Quero Ler</option>
                    <option value="LENDO">Lendo</option>
                    <option value="LIDO">Lido</option>
                    <option value="PENDENTE">Pendente</option>
                    <option value="ABANDONEI">Abandonei</option>
                  </select>
                </div>

                <div className="flex flex-col">
                  <label className="text-xs font-bold text-gray-500 mb-1">
                    Páginas Lidas
                  </label>
                  <input
                    type="number"
                    value={pagesRead}
                    onChange={(e) => setPagesRead(Number(e.target.value))}
                    className="h-10 border border-gray-300 rounded px-2 text-sm focus:border-primary focus:outline-none"
                    max={book.pageCount}
                  />
                </div>

                <div className="flex flex-col">
                  <label className="text-xs font-bold text-gray-500 mb-1">
                    Início
                  </label>
                  <input
                    type="date"
                    value={startDate}
                    onChange={(e) => setStartDate(e.target.value)}
                    className="h-10 border border-gray-300 rounded px-2 text-sm focus:border-primary focus:outline-none"
                  />
                </div>

                <div className="flex flex-col">
                  <label className="text-xs font-bold text-gray-500 mb-1">
                    Término
                  </label>
                  <input
                    type="date"
                    value={finishDate}
                    onChange={(e) => setFinishDate(e.target.value)}
                    className="h-10 border border-gray-300 rounded px-2 text-sm focus:border-primary focus:outline-none"
                  />
                </div>
              </div>
            </div>

            <div className="mt-auto">
              <button
                onClick={handleAddToShelf}
                disabled={loading}
                className="w-full py-4 bg-primary text-white font-bold rounded-md shadow-lg hover:bg-[#B89850] hover:scale-105 transition-all disabled:opacity-50 disabled:cursor-not-allowed"
              >
                {loading ? "Salvando..." : "Salvar na Estante"}
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
