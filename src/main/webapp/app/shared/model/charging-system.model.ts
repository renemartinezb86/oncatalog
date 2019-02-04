import { IBasicPO } from 'app/shared/model/basic-po.model';

export interface IChargingSystem {
    id?: string;
    serviceName?: string;
    offerTemplate?: string;
    basicPOS?: IBasicPO[];
}

export class ChargingSystem implements IChargingSystem {
    constructor(public id?: string, public serviceName?: string, public offerTemplate?: string, public basicPOS?: IBasicPO[]) {}
}
