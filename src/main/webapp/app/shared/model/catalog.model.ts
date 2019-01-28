import { Moment } from 'moment';
import { IBasicPO } from 'app/shared/model/basic-po.model';

export interface ICatalog {
    id?: string;
    name?: string;
    fileName?: string;
    createdDate?: Moment;
    basicPOs?: IBasicPO[];
}

export class Catalog implements ICatalog {
    constructor(
        public id?: string,
        public name?: string,
        public fileName?: string,
        public createdDate?: Moment,
        public basicPOs?: IBasicPO[]
    ) {}
}
