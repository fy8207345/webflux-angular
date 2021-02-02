export class ApiResult<T>{

  public static SUCCESS = 0

  code: number;
  msg: string;
  data: T;

}
