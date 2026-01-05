import { type RecoveryJwtToken, type UserLoginRequest, type UserRequest } from "../types/authTypes";
import ApiManager from "./apiManager";

const AuthService = {
  login: async (credentials: UserLoginRequest): Promise<RecoveryJwtToken> => {
    const response = await ApiManager.post<RecoveryJwtToken>('/user/login', credentials);
    return response.data;
  },

  register: async (userData: UserRequest): Promise<void> => {
    await ApiManager.post('/user/cadastrar', userData);
  },

  logout: () => {
    localStorage.removeItem('user_token');
    localStorage.removeItem('user_data');
  }
};

export default AuthService;
