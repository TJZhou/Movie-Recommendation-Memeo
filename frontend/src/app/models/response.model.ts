export class Response {
  code: number;
  msg: string;
  timestamp: string;
}

export class StringResponse extends Response {
  data: Array<string>;
}

export class NumberResponse extends Response {
  data: Array<number>;
}
