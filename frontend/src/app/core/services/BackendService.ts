import axios from "axios";
import { message } from "antd";
import auth from "./AuthService";
import { ErrorResponse } from "../interfaces/ErrorResponse";

class BackendService {
  API = "http://64.225.96.200:8081/api/";

  get(url: string, config: any) {
    const Authorization = this.addAuth();
    config.headers = { ...config.headers, Authorization, 'Access-Control-Allow-Origin':'*' };
    return axios.get(url, config);
  }

  post(url: string, body: any, config: any) {
    const Authorization = this.addAuth();
    config.headers = { ...config.headers, Authorization, 'Access-Control-Allow-Origin':'*' };
    return axios.post(url, { ...body }, config);
  }

  delete(url: string, config: any) {
    const Authorization = this.addAuth();
    config.headers = { ...config.headers, Authorization, 'Access-Control-Allow-Origin':'*' };
    return axios.delete(url, config);
  }

  addAuth() {
    return "Bearer " + auth.isAuthenticated();
  }

  getMetadata() {
    return this.get(this.API + "user/", {});
  }

  errorHandler(errorResponse: ErrorResponse) {
    if (errorResponse && errorResponse.status !== 429) {
      message.error(
        errorResponse.data.status.message ||
          "Something went wrong. Please, try again later."
      );
      if (errorResponse.data.status.code === 401) {
        auth.logout();
        window.location.reload();
      }
    } else {
      message.error("Too many requests. Try again later");
    }
  }
}

export const backendService = new BackendService();
