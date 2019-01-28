import { IBasicPO } from 'app/shared/model/basic-po.model';

export interface ICharacteristic {
    id?: string;
    name?: string;
    value?: string;
    basicPO?: IBasicPO;
}

export class Characteristic implements ICharacteristic {
    constructor(public id?: string, public name?: string, public value?: string, public basicPO?: IBasicPO) {}
}
