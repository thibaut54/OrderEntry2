import { IAccnt } from 'app/shared/model/accnt.model';

export interface IOrder {
  id?: number;
  orderId?: number;
  field1?: string;
  field2?: string;
  field3?: string;
  field4?: string;
  field5?: string;
  accnts?: IAccnt[];
  instrumentId?: number;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public orderId?: number,
    public field1?: string,
    public field2?: string,
    public field3?: string,
    public field4?: string,
    public field5?: string,
    public accnts?: IAccnt[],
    public instrumentId?: number
  ) {}
}
