import type { UserResponse } from "./userTypes";

export interface UserRequest {
  name: string;
  email: string;
  password: string;
}

export interface UserLoginRequest {
  email: string;
  password: string;
}

type UserRole = 'ROLE_ADMIN' | 'ROLE_CUSTOMER';

export interface UserLoginResponse {
  id: number;
  user: UserResponse;
  role: UserRole[];
}

type RoleType = 'ROLE_ADMIN' | 'ROLE_CUSTOMER';

export interface UserRecoveryResponse {
  id: number;
  email: string;
  roles: RoleType[];
}

export interface RecoveryJwtToken {
  token: string;
  user: UserLoginResponse;
}

export interface LoginCredentials {
  email: string;
  password: string;
}

export interface AuthContextType {
  signed: boolean;
  user: UserLoginResponse | null;
  loading: boolean;
  signIn: (credentials: LoginCredentials) => Promise<void>;
  signOut: () => void;
}
