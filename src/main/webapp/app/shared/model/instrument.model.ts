import { IOrder } from 'app/shared/model/order.model';

export interface IInstrument {
  id?: number;
  instrumentId?: number;
  field1?: string;
  field2?: string;
  field3?: string;
  field4?: string;
  field5?: string;
  orders?: IOrder[];
}

export class Instrument implements IInstrument {
  constructor(
    public id?: number,
    public instrumentId?: number,
    public field1?: string,
    public field2?: string,
    public field3?: string,
    public field4?: string,
    public field5?: string,
    public orders?: IOrder[]
  ) {}
}
