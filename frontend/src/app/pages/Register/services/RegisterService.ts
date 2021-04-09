import { backendService } from "../../../core/services/BackendService";
import { UserData } from "../interfaces/UserData";

class RegisterService {
  register(body: UserData) {
    const headers = {};
    return backendService.post(
      backendService.API + `auth/register`,
      body,
      headers
    );
  }
}

export const registerService = new RegisterService();
