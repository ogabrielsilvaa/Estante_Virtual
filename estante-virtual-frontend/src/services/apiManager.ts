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
      console.error("Sessão expirada ou inváldia. Deslogando...");

      localStorage.removeItem('user_token');
      localStorage.removeItem('user_data');

      window.location.href = '/login';
    }

    return Promise.reject(error);
  }
);

export default ApiManager;