export interface ErrorResponse {
  data: {
    status: {
      message: string;
      code: number;
    };
  };
  status?: number;
}
