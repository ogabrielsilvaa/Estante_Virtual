import { createContext, useContext, useEffect, useState, type ReactNode } from "react";
import type { AuthContextType, UserLoginRequest, UserLoginResponse } from "../types/authTypes";
import ApiManager from "../services/apiManager";
import AuthService from "../services/authService";

const AuthContext = createContext<AuthContextType>({} as AuthContextType);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<UserLoginResponse | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function loadStorageData() {
      const storedToken = localStorage.getItem('user_token');
      const storedUser = localStorage.getItem('user_data');

      if (storedToken && storedUser) {
        setUser(JSON.parse(storedUser));

        ApiManager.defaults.headers.Authorization = `Bearer ${storedToken}`;
      }

      setLoading(false);
    }

    loadStorageData();
  }, []);

  async function signIn(credentials: UserLoginRequest) {
    try {
      const data = await AuthService.login(credentials);
      const { token } = data;

      localStorage.setItem('user_token', token);
      localStorage.setItem('user_data', JSON.stringify(data));

      ApiManager.defaults.headers.Authorization = `Bearer ${token}`;

      setUser(data as unknown as UserLoginResponse);
      } catch (error) {
        throw error;
      }
    }

  function signOut() {
    AuthService.logout();

    setUser(null);
    delete ApiManager.defaults.headers.common['Authorization'];
  }

  return (
    <AuthContext.Provider value={{ signed: !!user, user, loading, signIn, signOut }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth deve ser usasdo dentro de um AuthProvider");
  }
  return context;
}