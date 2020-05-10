export class ErrorResponse extends Response {
  data: Array<Error>;
}

export class Error {
  errorType: string;
  errorMessage: string;
}
