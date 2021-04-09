import { backendService } from "../../../core/services/BackendService";

class UserPageService {
  getUser(username: string) {
    const headers = {};
    return backendService.get(
      backendService.API + `user/${username}`,
      headers
    );
  }
}

export const userPageService = new UserPageService();
