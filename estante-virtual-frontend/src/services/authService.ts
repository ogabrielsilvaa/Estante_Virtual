import { type RecoveryJwtToken, type UserLoginRequest } from "../types/authTypes";
import ApiManager from "./apiManager";

const AuthService = {
  login: async (credentials: UserLoginRequest): Promise<RecoveryJwtToken> => {
    const response = await ApiManager.post<RecoveryJwtToken>('/api/user/login', credentials);
    return response.data;
  },

  logout: () => {
    localStorage.removeItem('user_token');
    localStorage.removeItem('user_data');
    window.location.href = '/login';
  }
};

export default AuthService;
