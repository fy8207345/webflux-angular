export class LoginResponse{
  token: string;

  public constructor(init?: Partial<LoginResponse>) {
    Object.assign(this, init);
  }
}
