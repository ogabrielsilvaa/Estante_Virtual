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
    if (error.response && (error.response.status === 403 || error.response.status === 401)) {
      const requestUrl = error.config.url;

      const isAuthRequest = requestUrl.includes('/login') || requestUrl.includes('/cadastrar');

      if (!isAuthRequest) {
        console.error("Sessão expirada ou inválida. Deslogando...");

        localStorage.removeItem('user_token');
        localStorage.removeItem('user_data');

        if (window.location.pathname !== '/login') {
            window.location.href = '/login';
        }
      }
    }

    return Promise.reject(error);
  }
);

export default ApiManager;
