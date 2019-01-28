import { ICharacteristic } from 'app/shared/model/characteristic.model';
import { IOptionalService } from 'app/shared/model/optional-service.model';
import { ICatalog } from 'app/shared/model/catalog.model';

export interface IBasicPO {
    id?: string;
    poId?: string;
    name?: string;
    characteristics?: ICharacteristic[];
    optionalServices?: IOptionalService[];
    catalog?: ICatalog;
}

export class BasicPO implements IBasicPO {
    constructor(
        public id?: string,
        public poId?: string,
        public name?: string,
        public characteristics?: ICharacteristic[],
        public optionalServices?: IOptionalService[],
        public catalog?: ICatalog
    ) {}
}
