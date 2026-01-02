import type { UserResponse } from "./userTypes";

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

export interface AuthContextType {
  signed: boolean;
  user: UserLoginResponse | null;
  loading: boolean;
  signIn: (token: string, userData: UserLoginResponse) => void;
  signOut: () => void;
}
