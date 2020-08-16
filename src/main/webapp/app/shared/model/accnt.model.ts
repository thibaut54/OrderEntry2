export interface IAccnt {
  id?: number;
  acctCd?: string;
  acctName?: string;
  orderId?: number;
}

export class Accnt implements IAccnt {
  constructor(public id?: number, public acctCd?: string, public acctName?: string, public orderId?: number) {}
}
