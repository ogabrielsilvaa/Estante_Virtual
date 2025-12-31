import type { UserStatus } from "../enums/userStatus";

export interface UserRequest {
  name: string;
  email: string;
  password: string;
}

export interface UserAtualizarRequest {
  name: string;
}

export interface UserResponse {
  id: number;
  name: string;
  email: string;
  createdAt: string;
  status: UserStatus;
}
