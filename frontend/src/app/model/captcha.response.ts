export class CaptchaResponse{
  image: string;
  enabled: boolean;

  public constructor(init?: Partial<CaptchaResponse>) {
    Object.assign(this, init);
  }
}
