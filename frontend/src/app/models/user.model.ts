export class UserResponse extends Response {
  data: Array<User>;
}

export class User {
  userId: number;
  username: string;
}
