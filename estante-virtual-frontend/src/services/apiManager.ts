import axios from "axios";

export const ApiManager = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  responseType: 'json',
  headers: {
    'Content-Type': 'application/json',
  },
});

ApiManager.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('user_token');

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

ApiManager.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response) {
      const status = error.response.status;
      const requestUrl = error.config.url;

      const errorMessage = error.response.data?.message || "";

      const isAuthRequest = requestUrl.includes('/login') || requestUrl.includes('/cadastrar');

      const isBusinessError =
        errorMessage.includes("existe") ||
        errorMessage.includes("inválido") ||
        errorMessage.includes("obrigatório");

      if (!isAuthRequest) {
        if (status === 401 || (status === 403 && !isBusinessError)) {
          console.warn("Sessão expirada ou acesso negado crítico. Deslogando... ", status);

          localStorage.removeItem('user_token');
          localStorage.removeItem('user_data');

          if (window.location.pathname !== '/login') {
              window.location.href = '/login';
          }
        }
      }
    }

    return Promise.reject(error);
  }
);

export default ApiManager;
