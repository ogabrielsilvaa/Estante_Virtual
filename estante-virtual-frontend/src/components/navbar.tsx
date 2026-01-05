import React, { useState } from 'react';
import { FaSearch } from 'react-icons/fa';

interface NavbarProps {
  onSearch: (query: string) => void;
}

export function Navbar({ onSearch }: NavbarProps) {
  const [typingTimeout, setTypingTimeout] = useState<any>(null);

  function handleInputChange(e: React.ChangeEvent<HTMLInputElement>) {
    const text = e.target.value;

    if (typingTimeout) clearTimeout(typingTimeout);

    const newTimeout = setTimeout(() => {
      onSearch(text);
    }, 800);

    setTypingTimeout(newTimeout);
  }


  return (
    <nav className="bg-dark py-4 shadow-md w-full">
      <div className="w-full px-6 md:px-12 flex flex-col md:flex-row items-center justify-between gap-6 md:gap-0">

        <div className="text-center md:text-left">
          <a className="text-primary font-playfair font-medium text-[44px] block leading-tight cursor-pointer">
            Estante Virtual
          </a>
          
          <h2 className="text-[#A8A599] text-[18px]">
            Seu santuário de leitura pessoal
          </h2>
        </div>

        <form className="flex w-full md:w-auto justify-center">
          <div className="relative">
            <FaSearch className="absolute left-4 top-1/2 -translate-y-1/2 text-[#1A1A1F] z-10 pointer-events-none" />
            
            <input 
              className="w-[180px] h-[50px] bg-primary hover:bg-amber-300 text-white rounded-md pl-12 placeholder-[#1A1A1F] outline-none focus:bg-[#4a4a4a] transition-colors duration-300"
              type="search" 
              placeholder="Procurar Livros" 
              aria-label="Search" 
              onChange={handleInputChange}
            />
          </div>
        </form>

      </div>
    </nav>
  );
}