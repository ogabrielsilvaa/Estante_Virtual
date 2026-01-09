import { useLocation, useNavigate } from "react-router-dom";
import type { ReviewDetailsState } from "../types/reviewTypes";
import { useState } from "react";
import { Navbar } from "../components/navbar";
import { FaArrowLeft, FaBookOpen, FaCalendarAlt, FaFeatherAlt, FaUser } from "react-icons/fa";

const formatRating = (rating: string) => {
  const map: Record<string, string> = {
    EXCELENTE: "Excelente",
    MUITO_BOM: "Muito bom",
    BOM: "Bom",
    RAZOAVEL: "Razoável",
    FRACO: "Fraco",
  };
  return map[rating] || rating;
};

const formatDate = (dateString: string) => {
  if (!dateString) return "";
  const date = new Date(dateString);
  return new Intl.DateTimeFormat("pt-BR", {
    day: "2-digit",
    month: "long",
    year: "numeric",
  }).format(date);
};

export function ReviewDetails() {
  const navigate = useNavigate();
  const location = useLocation();

  const review = location.state as ReviewDetailsState;

  if (!review) {
    return (
      <div className="h-screen bg-dark flex items-center justify-center text-white">
        <p>
          Review não encontrada.{" "}
          <span
            onClick={() => navigate("/reviews")}
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

        <div
          className="bg-white border border-gray-800 rounded-xl shadow-2xl overflow-hidden flex flex-col md:flex-row min-h-[500px] cursor-pointer"
          onClick={() => navigate("/bookDetails", { state: review.book })}
        >
          <div className="md:w-1/3 bg-dark relative group">
            <img
              src={getCover(review.book.coverUrl)}
              alt={review.book.title}
              className="w-full h-full object-cover"
            />
            <div className="absolute inset-0 bg-gradient-to-t from-[#1A1A1F] via-transparent to-transparent md:bg-gradient-to-r md:from-transparent md:to-[#1A1A1F]/80" />
          </div>

          <div className="md:w-2/3 p-8 flex flex-col">
            <div className="mb-6 border-b border-gray-700 pb-6 relative">
              
              <span className="absolute top-0 right-0 px-3 py-1 text-xs font-bold uppercase tracking-wider text-[#1A1A1F] bg-primary rounded-full">
                {review.status || "Publicado"}
              </span>

              <h1 className="text-4xl font-bold text-primary font-playfair mb-2 leading-tight">
                {review.book.title}
              </h1>
              <h2 className="text-xl text-gray-400 font-playfair italic flex items-center gap-2">
                <FaFeatherAlt className="text-sm" />
                {review.book.author}
              </h2>

              <div className="flex flex-wrap gap-4 mt-4 text-sm text-gray-500">
                <div className="flex items-center gap-2">
                  <FaUser className="text-primary" />
                  <span>
                    Resenha por <strong className="text-gray-300">{review.user?.name || "Usuário"}</strong>
                  </span>
                </div>
                <div className="flex items-center gap-2">
                  <FaCalendarAlt className="text-primary" />
                  <span>{formatDate(review.createdAt)}</span>
                </div>
              </div>
            </div>

            <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-8">
              <RatingBox label="Enredo" value={review.ratingPlot} />
              <RatingBox label="Personagens" value={review.ratingCharacters} />
              <RatingBox label="Escrita" value={review.ratingWriting} />
              <RatingBox label="Imersão" value={review.ratingImmersion} />
            </div>

            <div className="flex-grow">
              <h3 className="text-primary font-bold uppercase tracking-widest text-sm mb-3 flex items-center gap-2">
                <FaBookOpen /> Análise Completa
              </h3>
              <div className="text-gray-300 leading-relaxed text-lg whitespace-pre-line font-light">
                {review.text ? (
                  review.text
                ) : (
                  <span className="italic text-gray-600">Nenhum comentário escrito para esta review.</span>
                )}
              </div>
            </div>

            <div className="mt-auto pt-4 border-t border-gray-100 flex justify-center">
              <p className="text-primary font-bold text-sm uppercase tracking-widest animate-pulse group-hover:animate-none group-hover:scale-105 transition-transform duration-300">
                Clique no card para ver o livro
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

function RatingBox({ label, value }: { label: string; value: string }) {
  return (
    <div className="bg-gray-50 p-3 rounded-lg border border-gray-200 text-center shadow-sm hover:border-primary/50 transition-colors">
      <p className="text-xs text-gray-500 uppercase tracking-wide mb-1">{label}</p>
      <p className="text-primary font-bold font-playfair text-lg">
        {formatRating(value)}
      </p>
    </div>
  );
}
