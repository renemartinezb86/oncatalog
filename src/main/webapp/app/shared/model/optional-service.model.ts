import { IBasicPO } from 'app/shared/model/basic-po.model';

export interface IOptionalService {
    id?: string;
    serviceId?: string;
    cardinality?: string;
    group?: string;
    basicPO?: IBasicPO;
}

export class OptionalService implements IOptionalService {
    constructor(
        public id?: string,
        public serviceId?: string,
        public cardinality?: string,
        public group?: string,
        public basicPO?: IBasicPO
    ) {}
}
