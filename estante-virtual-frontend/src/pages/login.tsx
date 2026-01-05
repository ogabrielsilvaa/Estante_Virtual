import { useNavigate } from "react-router-dom";
import { FormInput } from "../components/formInput";
import { useAuth } from "../context/authContext";
import { useState } from "react";

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
    <div className="relative flex flex-col items-center justify-center h-screen">

      <div className="mb-3">
        <h1 className="text-primary font-playfair font-medium text-[64px] block leading-tight">
          Estante Virtual
        </h1>
      </div>

      <div className="w-[450px] flex flex-col gap-4 [&>div]:!w-full [&>div]:!m-0 [&_input]:!h-14 [&_input]:!text-lg">
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

      <div className="w-[450px] flex gap-4 mt-8">
        <button
          type="button"
          onClick={() => navigate("/register")}
          className="w-full py-4 text-white text-lg bg-blue-600 box-border rounded-md hover:bg-blue-600 hover:scale-105 transition-all duration-300 focus:ring-4 focus:ring-success-medium shadow-xs font-medium leading-5 rounded-base text-sm px-4 py-2.5 focus:outline-none">
            Cadastro
          </button>

        <button
        type="button"
        onClick={handleLogin}
        className="w-full py-4 text-white text-lg bg-primary box-border rounded-md hover:bg-[#B89850] hover:scale-105 transition-all duration-300 focus:ring-4 focus:ring-success-medium shadow-xs font-medium leading-5 rounded-base text-sm px-4 py-2.5 focus:outline-none">
          {loading ? "Entrando.." : "Avançar"}
        </button>
      </div>

      <h3 className="absolute bottom-8 text-primary text-sm opacity-70">Desenvolvido por Gabriel Silva</h3>
    </div>
  );
}
