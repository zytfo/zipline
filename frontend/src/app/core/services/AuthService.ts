import { cookieService } from "./CookieService";
import { User } from "../interfaces/User";

class Auth {
  TOKEN_KEY = "_token";
  token: string | null = null;
  metadata: any = null;

  constructor() {
    this.token = cookieService.get(this.TOKEN_KEY);
    this.metadata = cookieService.get("_metadata");
  }

  /**
   * Checks if user authenticated
   */
  isAuthenticated() {
    return this.token;
  }

  /**
   * Returns if user has metadata
   */
  hasMetadata() {
    return this.metadata;
  }

  /**
   * Returns user metadata if present
   */
  getMetadata() {
    return this.metadata;
  }

  /**
   * Saves user authentication token
   * @param token
   */
  saveToken(token: string) {
    cookieService.set(this.TOKEN_KEY, token, { path: "/" });
  }

  /**
   * Sets user roles
   * @param roles
   */
  setRoles(roles: string[]) {
    cookieService.set("_roles", JSON.stringify(roles), { path: "/" });
  }

  /**
   * Saves user metadata
   * @param user
   */
  saveMetadata(user: User) {
    cookieService.set("_metadata", JSON.stringify(user), {
      path: "/",
    });
    this.metadata = user;
  }

  /**
   * Deletes authentication token
   */
  deleteToken() {
    cookieService.remove(this.TOKEN_KEY);
  }

  /**
   * Deletes user roles
   */
  deleteRoles() {
    cookieService.remove("_roles");
  }

  /**
   * Deletes user metadata
   */
  deleteMetadata() {
    cookieService.remove("_metadata");
  }

  /**
   * Logs in user
   * @param user
   */
  login(user: User) {
    this.token = user.token;
    this.saveToken(user.token);
    this.setRoles(user.roles);
    this.saveMetadata(user);
  }

  /**
   * Clear all the user data
   */
  logout() {
    this.token = null;
    this.deleteToken();
    this.deleteRoles();
  }
}

const auth = new Auth();

export default auth;
