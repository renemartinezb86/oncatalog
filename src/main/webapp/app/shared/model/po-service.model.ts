import { IBasicPO } from 'app/shared/model/basic-po.model';

export const enum PoServiceType {
    BASIC = 'BASIC',
    SERVICE = 'SERVICE',
    BARRING = 'BARRING'
}

export interface IPoService {
    id?: string;
    name?: string;
    type?: PoServiceType;
    basicPOS?: IBasicPO[];
}

export class PoService implements IPoService {
    constructor(public id?: string, public name?: string, public type?: PoServiceType, public basicPOS?: IBasicPO[]) {}
}
