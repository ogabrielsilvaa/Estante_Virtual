import { useNavigate } from "react-router-dom";
import { FormInput } from "../components/formInput";
import { useAuth } from "../context/authContext";
import { useState } from "react";
import bookLogo from "../assets/images/bookLogo.png";

export function Login() {
  const navigate = useNavigate();
  const { signIn } = useAuth();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  async function handleLogin() {
    if (!email || !password) {
      alert("Por favor, preencha todos os campos.");
      return;
    }

    setLoading(true);

    try {
      await signIn({ email, password });
      navigate("/myShelf");
    } catch (error) {
      console.error(error);
      alert("Erro ao entrar. Verifique seu e-mail e senha.");
    } finally {
      setLoading(false);
    }
  }


  return (
    <div className="relative flex flex-col items-center justify-center h-screen px-4">
      <img
        src={bookLogo}
        alt="Logo do Livro"
        className="w-40 md:w-60 h-auto transition-all duration-300"
      />
      
      <div className="mb-3 text-center">
        <h1 className="text-primary font-playfair font-medium text-4xl md:text-[64px] block leading-tight transition-all duration-300">
          Estante Virtual
        </h1>
      </div>

      <div className="w-[85%] max-w-[320px] md:w-full md:max-w-[450px] flex flex-col gap-4 [&>div]:!w-full [&>div]:!m-0 [&_input]:!h-12 md:[&_input]:!h-14 [&_input]:!text-base md:[&_input]:!text-lg transition-all duration-300">
        <FormInput
          title="Email"
          placeholder="Digite seu email"
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <FormInput
          title="Senha"
          placeholder="Digite sua senha"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
      </div>

      <div className="w-[85%] max-w-[320px] md:w-full md:max-w-[450px] flex flex-col md:flex-row gap-3 md:gap-4 mt-6 md:mt-8">
        <button
          type="button"
          onClick={() => navigate("/register")}
          className="w-full flex-1 py-2.5 md:py-4 text-sm md:text-lg text-white bg-blue-600 box-border rounded-md font-medium shadow-xs hover:bg-blue-600 hover:scale-105 transition-all duration-300 focus:outline-none focus:ring-4 focus:ring-success-medium">
            Cadastro
          </button>

        <button
        type="button"
        onClick={handleLogin}
        className="w-full flex-1 py-2.5 md:py-4 text-sm md:text-lg text-white bg-primary box-border rounded-md font-medium shadow-xs hover:bg-[#B89850] hover:scale-105 transition-all duration-300 focus:outline-none focus:ring-4 focus:ring-success-medium">
          {loading ? "Entrando.." : "Avançar"}
        </button>
      </div>

      <h3 className="mt-5 text-primary text-xs md:text-sm opacity-70 text-center w-full">Desenvolvido por Gabriel Silva</h3>
    </div>
  );
}
