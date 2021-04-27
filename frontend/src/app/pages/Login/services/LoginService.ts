import { backendService } from "../../../core/services/BackendService";

class LoginService {
  login(username: string, password: string) {
    const headers = {};
    const body = {
      username: username,
      password: password,
    };
    return backendService.post(
      backendService.API + `auth/login`,
      body,
      headers
    );
  }
}

export const loginService = new LoginService();
