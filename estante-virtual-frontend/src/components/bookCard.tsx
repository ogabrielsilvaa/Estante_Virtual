import { FaHeart, FaTrash } from "react-icons/fa";
import { BookReadingStatus } from "../enums/bookReadingStatus";

interface BookCardProps {
  id?: number;
  coverUrl?: string | null;
  title?: string;
  author?: string;
  readingStatus: BookReadingStatus;
  pagesRead?: number;
  pageCount?: number;
  onClick?: () => void;
  onDelete?: (e: React.MouseEvent) => void;
  isFavorite?: boolean;
}

const getCover = (url?: string | null) => {
  return url ? url : "https://placehold.co/128x190?text=Sem+Capa";
};

const getStatusBadgeStyle = (status: BookReadingStatus) => {
  switch (status) {
      case BookReadingStatus.LENDO:
        return "bg-blue-900/30 text-blue-300 border border-blue-800";
      case BookReadingStatus.LIDO:
        return "bg-green-900/30 text-green-300 border border-green-800";
      case BookReadingStatus.QUERO_LER:
        return "bg-yellow-900/30 text-yellow-300 border border-yellow-800";
      case BookReadingStatus.ABANDONEI:
        return "bg-yellow-900/30 text-yellow-300 border border-yellow-800";
      default:
        return "bg-gray-800 text-gray-400 border border-gray-700";
    }
};

const formatStatus = (status: BookReadingStatus) => {
  return status.toString().replace(/_/g, " ");
};

export function BookCard({
  title,
  author,
  coverUrl,
  readingStatus,
  pagesRead = 0,
  pageCount = 0,
  isFavorite,
  onClick,
  onDelete,
}: BookCardProps) {
  const calculateProgress = () => {
    if (!pageCount || pageCount === 0) return 0;
    const percent = (pagesRead / pageCount) * 100;
    return Math.min(Math.round(percent), 100);
  };

  const progressPercent = calculateProgress();
  const showProgressBar = readingStatus === BookReadingStatus.LENDO && pageCount > 0;

  return (
    <div
      onClick={onClick}
      className="bg-[#24242B] group relative flex flex-row h-44 w-full cursor-pointer overflow-hidden rounded-xl p-3 border border-[#45453E] transition-all duration-300 ease-out hover:-translate-y-1 hover:border-primary hover:shadow-[0_0_20px_rgba(201,169,97,0.2)]"
    >
      {isFavorite && (
        <div className="absolute top-2 right-2 z-20 text-red-500 bg-[#1A1A1F] rounded-full p-1.5 shadow-md border border-gray-800">
          <FaHeart size={12} />
        </div>
      )}

      {onDelete && (
        <button
          onClick={(e) => {
            e.stopPropagation();
            onDelete(e);
          }}
          className="absolute top-2 left-2 z-20 text-gray-400 bg-[#1A1A1F]/90 hover:bg-red-900/50 hover:text-red-400 rounded-full p-1.5 shadow-md transition-all opacity-0 group-hover:opacity-100 border border-transparent hover:border-red-800"
          title="Remover"
        >
          <FaTrash size={12} />
        </button>
      )}

      <div className="relative w-28 h-full flex-shrink-0 overflow-hidden rounded-lg shadow-black/50 shadow-md">
        <img
          src={getCover(coverUrl)}
          alt={title}
          className="h-full w-full object-cover group-hover:opacity-90 transition-opacity"
        />
      </div>

      <div className="flex flex-1 flex-col justify-between pl-4 mr-6 font-sans">
        <div>
          <div className="mb-2">
            <span className={`text-[9px] font-bold px-2 py-0.5 rounded-full uppercase tracking-wider ${getStatusBadgeStyle(readingStatus)}`}>
              {formatStatus(readingStatus)}
            </span>
          </div>

          <h3 className="font-bold text-white leading-snug mb-1 line-clamp-2" title={title}>
            {title}
          </h3>
          <p className="text-xs text-gray-400 font-medium truncate">
            {author}
          </p>
        </div>

        {showProgressBar ? (
          <div className="mt-2">
            <div className="flex justify-between items-end mb-1">
              <span className="text-[10px] text-gray-500 font-medium">
                {pagesRead} / {pageCount} pág
              </span>
              <span className="text-xs font-bold text-primary">
                {progressPercent}%
              </span>
            </div>
            
            <div className="w-full bg-[#1A1A1F] rounded-full h-1.5 border border-white/5">
              <div
                className="bg-primary h-1.5 rounded-full shadow-[0_0_10px_rgba(201,169,97,0.4)]"
                style={{ width: `${progressPercent}%` }}
              ></div>
            </div>
          </div>
        ) : (
          <div className="h-4"></div>
        )}
      </div>
    </div>
  );
}
