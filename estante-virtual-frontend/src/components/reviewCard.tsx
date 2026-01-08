import { FaRegStar, FaStar, FaUserCircle } from "react-icons/fa";
import { RatingLevel } from "../enums/ratingLevel";
import type { BookResponse } from "../types/bookTypes";
import type { UserResponse } from "../types/userTypes";

interface ReviewCardProps {
  id?: number;
  user?: UserResponse;
  book?: BookResponse;
  coverUrl?: string | null;
  ratingPlot?: RatingLevel;
  ratingCharacters?: RatingLevel;
  ratingWriting?: RatingLevel;
  ratingImmersion?: RatingLevel;
  text?: string;
  createdAt?: string;
  onClick?: () => void;
}

const getCover = (url?: string | null) => {
  return url ? url : "https://placehold.co/128x190?text=Sem+Capa";
};

const mapRatingToStars = (level?: RatingLevel | string): number => {
  if (!level) return 0;

  const valor = level.toString();

  switch (valor) {
    case "Excelente": return 5;
    case "Muito Bom": return 4;      
    case "Bom": return 3;
    case "Razoável": return 2;
    case "Fraco": return 1;
    default:
      console.log("Rating não reconhecido:", valor);
      return 0;
  }
};

const StarRating = ({ score }: { score: number }) => {
  return (
    <div className="flex gap-0.5">
      {[...Array(5)].map((_, i) => (
        <span key={i} className="text-sm">
          {i < score ? (
            <FaStar className="text-[#C9A961]" />
          ) : (
            <FaRegStar className="text-gray-600" />
          )}
        </span>
      ))}
    </div>
  );
};

export function ReviewCard({
  user,
  book,
  coverUrl,
  ratingPlot,
  ratingCharacters,
  ratingWriting,
  ratingImmersion,
  text,
  createdAt,
  onClick
}: ReviewCardProps) {
  const formattedDate = createdAt 
    ? new Date(createdAt).toLocaleDateString('pt-BR') 
    : "";

  return (
    <div
      onClick={onClick}
      className="bg-[#24242B] border border-[#45453E] w-full rounded-xl p-4 md:p-6 mb-6 flex flex-col md:flex-row gap-6 hover:border-primary transition-colors cursor-pointer shadow-lg"
    >
      <div className="w-32 md:w-40 flex-shrink-0 mx-auto md:mx-0">        
        <div className="aspect-[2/3] rounded-lg overflow-hidden shadow-black/50 shadow-md relative">
          <img
            src={getCover(coverUrl || book?.coverUrl)}
            alt={book?.title || "Livro"}
            className="h-full w-full object-cover hover:scale-105 transition-transform duration-500"
          />
        </div>
      </div>

      <div className="flex-1 flex flex-col">
        
        <div className="flex justify-between items-start mb-3 border-b border-gray-700 pb-3">
          <div className="flex items-center gap-3">
            <div className="w-10 h-10 rounded-full bg-gray-700 flex items-center justify-center text-gray-400 overflow-hidden">
               <FaUserCircle size={28} />
            </div>
            <div>
              <h4 className="text-white font-bold text-sm md:text-base leading-tight">
                {user?.name || "Usuário Anônimo"}
              </h4>
              <span className="text-xs text-gray-400">
                Resenha de <span className="text-primary font-medium">{book?.title}</span>
              </span>
            </div>
          </div>
          
          <span className="text-xs text-gray-500 font-medium whitespace-nowrap">
            {formattedDate}
          </span>
        </div>

        <div className="flex-1 mb-4">
          <p className="text-gray-300 text-sm leading-relaxed line-clamp-4 md:line-clamp-none">
            {text ? `"${text}"` : <span className="italic text-gray-600">Sem comentário escrito.</span>}
          </p>
        </div>

        <div className="bg-[#1A1A1F] rounded-lg p-3 md:p-4 grid grid-cols-2 md:grid-cols-4 gap-4 border border-gray-800">
          
          <div className="flex flex-col gap-1">
            <span className="text-[10px] uppercase tracking-wider text-gray-500 font-bold">Enredo</span>
            <StarRating score={mapRatingToStars(ratingPlot)} />
          </div>

          <div className="flex flex-col gap-1">
            <span className="text-[10px] uppercase tracking-wider text-gray-500 font-bold">Personagens</span>
            <StarRating score={mapRatingToStars(ratingCharacters)} />
          </div>

          <div className="flex flex-col gap-1">
            <span className="text-[10px] uppercase tracking-wider text-gray-500 font-bold">Escrita</span>
            <StarRating score={mapRatingToStars(ratingWriting)} />
          </div>

          <div className="flex flex-col gap-1">
            <span className="text-[10px] uppercase tracking-wider text-gray-500 font-bold">Imersão</span>
            <StarRating score={mapRatingToStars(ratingImmersion)} />
          </div>

        </div>

      </div>
    </div>
  );
}
