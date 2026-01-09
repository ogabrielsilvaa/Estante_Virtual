import { useEffect, useState } from "react";
import { Navbar } from "../components/navbar";
import { useNavigate } from "react-router-dom";
import type { ReviewResponse } from "../types/reviewTypes";
import ReviewService from "../services/reviewService";
import { ReviewCard } from "../components/reviewCard";
import { ReviewStatus } from "../enums/reviewStatus";

export function Reviews() {
  const navigate = useNavigate();

  const [reviews, setReviews] = useState<ReviewResponse[]>([]);
  const [activeTab, setActiveTab] = useState<"community" | "mine">("community");
  const [loading, setLoading] = useState(false);
  const [pagination, setPagination] = useState({ page: 0, totalPages: 0 });

  useEffect(() => {
    fetchData();
  }, [activeTab]);

  async function fetchData() {
    setLoading(true);
    setReviews([]);
    try {
      let data;

      if (activeTab === "community") {
        console.log("Buscando feed da comunidade");
        data = await ReviewService.viewFeed();
      } else {
        console.log("Buscando minhas reviews");
        data = await ReviewService.viewReviews(ReviewStatus.PUBLICADO);
      }

      console.log("Dados recebidos do Backend: ", data.content);

      setReviews(data.content);

      setPagination({
        page: data.number,
        totalPages: data.totalPages,
      });
    } catch (error) {
      console.error("Erro ao carregar reviews:", error);
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="min-h-screen bg-dark pb-10">
      <Navbar />

      <div className="w-full max-w-[1000px] mx-auto px-6 mt-8">
        <div className="flex gap-8 items-end mb-8 border-b border-gray-800 pb-2">
          <button
            onClick={() => setActiveTab("community")}
            className={`text-3xl font-bold font-playfair transition-colors duration-300 relative px-1 pb-1
              ${activeTab === "community" ? "text-primary" : "text-gray-500 hover:text-gray-300"}
            `}
          >
            Reviews da Comunidade
            <span 
              className={`absolute bottom-[-9px] left-0 w-full h-1 bg-primary rounded-t-md transition-transform duration-300 origin-left
              ${activeTab === "community" ? "scale-x-100" : "scale-x-0"}
              `}
            />
          </button>

          <button
            onClick={() => setActiveTab("mine")}
            className={`text-2xl font-bold font-playfair transition-colors duration-300 relative px-1 pb-1 mb-[2px]
              ${activeTab === "mine" ? "text-primary" : "text-gray-600 hover:text-gray-400"}
            `}
          >
            Minhas Reviews
            <span 
              className={`absolute bottom-[-9px] left-0 w-full h-1 bg-primary rounded-t-md transition-transform duration-300 origin-left
              ${activeTab === "mine" ? "scale-x-100" : "scale-x-0"}
              `} 
            />
          </button>
        </div>

        <div className="flex flex-col gap-6">
          {loading ? (
             <div className="py-10 text-center">
                <p className="text-gray-400 animate-pulse">Carregando...</p>
             </div>
          ) : reviews.length > 0 ? (
            reviews.map((review) => (
              <ReviewCard
                key={review.id}
                user={review.user}
                book={review.book}
                coverUrl={review.book.coverUrl}
                ratingPlot={review.ratingPlot}
                ratingCharacters={review.ratingCharacters}
                ratingWriting={review.ratingWriting}
                ratingImmersion={review.ratingImmersion}
                text={review.text}
                createdAt={review.createdAt}
                onClick={() => navigate("/bookDetails", { state: { ...review.book, ...review } })}
              />
            ))
          ) : (
            <div className="text-center py-10 border border-dashed border-gray-800 rounded-lg">
               <p className="text-xl text-gray-400 font-playfair">
                 {activeTab === "community" 
                   ? "Ainda não há reviews na comunidade." 
                   : "Você ainda não publicou nenhuma review."}
               </p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
