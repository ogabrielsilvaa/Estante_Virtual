import { FaSearch } from 'react-icons/fa';

export function Navbar() {
  return (
    <nav className="bg-dark py-4 shadow-md">
      <div className="max-w-[1200px] mx-auto px-8 h-24 flex justify-between items-center">

        <div>
          <a className="text-primary font-playfair font-medium text-[44px] block leading-tight cursor-pointer">
            Estante Virtual
          </a>
          
          <h2 className="text-[#A8A599] text-[18px]">
            Seu santuário de leitura pessoal
          </h2>
        </div>

        <form className="flex">
          <div className="relative">
            <FaSearch className="absolute left-4 top-1/2 -translate-y-1/2 text-[#1A1A1F] z-10 pointer-events-none" />
            
            <input 
              className="w-[180px] h-[50px] bg-primary hover:bg-amber-300 text-white rounded-md pl-12 placeholder-[#1A1A1F] outline-none focus:bg-[#4a4a4a] transition-colors duration-300"
              type="search" 
              placeholder="Search Books" 
              aria-label="Search" 
            />
          </div>
        </form>

      </div>
    </nav>
  );
}