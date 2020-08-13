import { IAccnt } from 'app/shared/model/accnt.model';

export interface IOrder {
  id?: number;
  orderInitiator?: string;
  sideCombo?: string;
  quantity?: string;
  orderType?: string;
  goodTill?: string;
  tradingInstruction?: string;
  accnts?: IAccnt[];
  instrumentId?: number;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public orderInitiator?: string,
    public sideCombo?: string,
    public quantity?: string,
    public orderType?: string,
    public goodTill?: string,
    public tradingInstruction?: string,
    public accnts?: IAccnt[],
    public instrumentId?: number
  ) {}
}
