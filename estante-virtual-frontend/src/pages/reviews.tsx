import { useEffect, useState } from "react";
import { Navbar } from "../components/navbar";
import { useNavigate } from "react-router-dom";
import type { ReviewResponse } from "../types/reviewTypes";
import ReviewService from "../services/reviewService";
import { ReviewCard } from "../components/reviewCard";

export function Reviews() {
  const navigate = useNavigate();

  const [reviews, setReviews] = useState<ReviewResponse[]>([]);

  const [loading, setLoading] = useState(false);

  const [pagination, setPagination] = useState({ page: 0, totalPages: 0 });

  useEffect(() => {
    fetchReviews();
  }, []);

  async function fetchReviews() {
    setLoading(true);
    try {
      const data = await ReviewService.viewFeed();

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
        <div className="flex justify-between items-center mb-8 border-b border-gray-800 pb-4">
          <h1 className="text-3xl font-bold text-primary font-playfair">
            {reviews.length > 0 ? "Reviews da Comunidade" : "Ainda não há reviews"}
          </h1>
        </div>

        <div className="flex flex-col gap-6">
          {reviews.map((review) => (
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
              onClick={() => navigate("/bookDetails", { state: {...review.book, ...review } })}
            />
          ))}
        </div>
      </div>
    </div>
  );
}
