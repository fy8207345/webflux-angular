export class ApiResult<T>{

  public static SUCCESS = 0

  code: number;
  msg?: string;
  data?: T;

  public success(){
    return this.code === ApiResult.SUCCESS;
  }

  public constructor(init?: Partial<ApiResult<T>>) {
    Object.assign(this, init);
  }
}
