import { ICatalog } from 'app/shared/model/catalog.model';
import { ICharacteristic } from 'app/shared/model/characteristic.model';
import { IOptionalService } from 'app/shared/model/optional-service.model';

export interface IBasicPO {
    id?: string;
    poId?: string;
    name?: string;
    catalog?: ICatalog;
    characteristics?: ICharacteristic[];
    optionalServices?: IOptionalService[];
}

export class BasicPO implements IBasicPO {
    constructor(
        public id?: string,
        public poId?: string,
        public name?: string,
        public catalog?: ICatalog,
        public characteristics?: ICharacteristic[],
        public optionalServices?: IOptionalService[]
    ) {}
}
