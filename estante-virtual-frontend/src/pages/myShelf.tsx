import { Navbar } from "../components/navbar";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/authContext";

export function MyShelf() {
  const navigate = useNavigate();
  const { signOut } = useAuth();

  async function handleLogOut() {
    signOut();
    navigate("/login");
  }

  return (
    <>
      <Navbar />

      <div className="w-full flex flex-col items-center mt-10 px-4">
        <h1 className="text-white">Esta é minha estante</h1>

        <button
          type="button"
          onClick={handleLogOut}
          className="absolute bottom-8 w-40 py-3 text-white text-base bg-red-600 rounded-md hover:bg-red-700 hover:scale-105 transition-all duration-300 shadow-md font-medium focus:outline-none"
        >
          Sair
        </button>
      </div>
    </>
  );
}
