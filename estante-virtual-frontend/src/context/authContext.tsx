import { createContext, useContext, useEffect, useState, type ReactNode } from "react";
import { type AuthContextType, type UserLoginResponse } from "../types/authTypes";
import ApiManager from "../services/apiManager";

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

  function signIn(token: string, userData: UserLoginResponse) {
    setUser(userData);

    localStorage.setItem('user_token', token);
    localStorage.setItem('user_data', JSON.stringify(userData));

    ApiManager.defaults.headers.Authorization = `Bearer ${token}`;
  }

  function signOut() {
    localStorage.removeItem('user_token');
    localStorage.removeItem('user_data');
    setUser(null);

    window.location.href = '/login';
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