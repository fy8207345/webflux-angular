export class JwtToken {
  token: string;
  expireTime: Date;

  public constructor(init?: Partial<JwtToken>) {
    Object.assign(this, init);

    if(init.expireTime){
      this.expireTime = new Date(init.expireTime);
    }
  }
}
