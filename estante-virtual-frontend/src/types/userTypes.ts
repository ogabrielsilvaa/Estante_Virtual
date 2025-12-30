import type { UserStatus } from "../enums/userStatus";

export interface UserRequest {
  name: string;
  email: string;
  password: string;
}

export interface UserAtualizarRequest {
  name: string;
}

export interface UserLoginRequest {
  email: string;
  password: string;
}

export interface UserResponse {
  id: number;
  name: string;
  email: string;
  createdAt: string;
  status: UserStatus;
}


type UserRole = 'ROLE_ADMIN' | 'ROLE_CUSTOMER';

export interface UserLoginResponse {
  id: number;
  name: string;
  email: string;
  createdAt: string;
  status: UserStatus;
  role: UserRole[];
}


type RoleType = 'ROLE_ADMIN' | 'ROLE_CUSTOMER';

export interface UserRecoveryResponse {
  id: number;
  email: string;
  roles: RoleType[];
}