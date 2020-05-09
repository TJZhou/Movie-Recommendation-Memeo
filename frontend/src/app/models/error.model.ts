export class ErrorResponse {
  code: number;
  msg: string;
  timestamp: string;
  data: Array<Error>;
}

export class Error {
  errorType: string;
  errorMessage: string;
}
