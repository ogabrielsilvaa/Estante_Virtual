import { useNavigate } from "react-router-dom";
import { FormInput } from "../components/formInput";
import { useState } from "react";
import AuthService from "../services/authService";
import bookLogo from "../assets/images/bookLogo.png";

export function Register() {
  const navigate = useNavigate();

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  async function handleRegister() {
    if (!name || !email || !password) {
      alert("Preencha todos os campos!");
      return;
    }

    setLoading(true);

    try {
      await AuthService.register({
        name: name,
        email: email,
        password: password
      });

      alert("Cadastro realizado com sucesso!");
      navigate("/myShelf");
    } catch (error) {
      console.error(error);
      alert("Erro ao cadastrar. Tente novamente.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="relative flex flex-col items-center justify-center min-h-screen px-4 py-10 md:py0">
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
          title="Nome"
          placeholder="Digite seu nome"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />

        <FormInput
          title="Email"
          placeholder="Digite seu email"
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <FormInput
          title="Senha"
          placeholder="Digite seu senha"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
      </div>

      <div className="w-[85%] max-w-[320px] md:w-full md:max-w-[450px] flex flex-col md:flex-row gap-3 md:gap-4 mt-6 md:mt-8">
        <button
          type="button"
          onClick={() => navigate("/login")}
          className="w-full flex-1 py-2.5 md:py-4 text-sm md:text-lg text-white bg-blue-600 box-border rounded-md font-medium shadow-xs hover:bg-blue-600 hover:scale-105 transition-all duration-300 focus:outline-none focus:ring-4 focus:ring-success-medium">
            Login
          </button>

        <button
          type="button"
          onClick={handleRegister}
          className="w-full flex-1 py-2.5 md:py-4 text-sm md:text-lg text-white bg-primary box-border rounded-md font-medium shadow-xs hover:bg-[#B89850] hover:scale-105 transition-all duration-300 focus:outline-none focus:ring-4 focus:ring-success-medium">
            {loading ? "Entrando.." : "Registrar"}
        </button>
      </div>

      <h3 className="mt-5 text-primary text-sm opacity-70">Desenvolvido por Gabriel Silva</h3>
    </div>
  );
}
