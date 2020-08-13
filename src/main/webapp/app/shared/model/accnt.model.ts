export interface IAccnt {
  id?: number;
  accountId?: number;
  field1?: string;
  field2?: string;
  field3?: string;
  field4?: string;
  field5?: string;
  orderId?: number;
}

export class Accnt implements IAccnt {
  constructor(
    public id?: number,
    public accountId?: number,
    public field1?: string,
    public field2?: string,
    public field3?: string,
    public field4?: string,
    public field5?: string,
    public orderId?: number
  ) {}
}
