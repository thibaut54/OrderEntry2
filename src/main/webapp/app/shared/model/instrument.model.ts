import { IOrder } from 'app/shared/model/order.model';

export interface IInstrument {
  id?: number;
  name?: string;
  bloombergTicker?: string;
  currencyQuotation?: string;
  isin?: string;
  market?: string;
  orders?: IOrder[];
}

export class Instrument implements IInstrument {
  constructor(
    public id?: number,
    public name?: string,
    public bloombergTicker?: string,
    public currencyQuotation?: string,
    public isin?: string,
    public market?: string,
    public orders?: IOrder[]
  ) {}
}
