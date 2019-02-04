import { IBasicPO } from 'app/shared/model/basic-po.model';

export interface IBSCS {
    id?: string;
    serviceName?: string;
    bscsService?: string;
    basicPOS?: IBasicPO[];
}

export class BSCS implements IBSCS {
    constructor(public id?: string, public serviceName?: string, public bscsService?: string, public basicPOS?: IBasicPO[]) {}
}
