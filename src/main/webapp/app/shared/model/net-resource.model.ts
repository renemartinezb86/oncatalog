import { IBasicPO } from 'app/shared/model/basic-po.model';

export interface INetResource {
    id?: string;
    name?: string;
    parameter?: string;
    value?: string;
    basicPOS?: IBasicPO[];
}

export class NetResource implements INetResource {
    constructor(public id?: string, public name?: string, public parameter?: string, public value?: string, public basicPOS?: IBasicPO[]) {}
}
