import type { UserAtualizarRequest, UserRequest, UserResponse } from "../types/userTypes";
import ApiManager from "./apiManager";

const UserService = {
  userRegister: async (data: UserRequest): Promise<UserResponse> => {
      const response = await ApiManager.post<UserResponse>('/user/cadastrar', data);
      return response.data;
  },

  searchProfile: async (): Promise<UserResponse> => {
    const response = await ApiManager.get<UserResponse>('/user/buscarMeuPerfil');
    return response.data;
  },

  updateProfile: async (data: UserAtualizarRequest): Promise<UserResponse> => {
      const response = await ApiManager.patch<UserResponse>('/user/atualizar', data);
      return response.data;
  },

  deleteProfile: async (): Promise<void> => {
    await ApiManager.delete<void>('/user/deletarMinhaConta');
  },
};

export default UserService;
