import { ICharacteristic } from 'app/shared/model/characteristic.model';
import { IOptionalService } from 'app/shared/model/optional-service.model';
import { IPoService } from 'app/shared/model/po-service.model';
import { INetResource } from 'app/shared/model/net-resource.model';
import { IChargingSystem } from 'app/shared/model/charging-system.model';
import { IBSCS } from 'app/shared/model/bscs.model';
import { ICatalog } from 'app/shared/model/catalog.model';

export interface IBasicPO {
    id?: string;
    poId?: string;
    name?: string;
    characteristics?: ICharacteristic[];
    optionalServices?: IOptionalService[];
    poServices?: IPoService[];
    netResources?: INetResource[];
    chargingSystems?: IChargingSystem[];
    bSCSses?: IBSCS[];
    catalog?: ICatalog;
}

export class BasicPO implements IBasicPO {
    constructor(
        public id?: string,
        public poId?: string,
        public name?: string,
        public characteristics?: ICharacteristic[],
        public optionalServices?: IOptionalService[],
        public poServices?: IPoService[],
        public netResources?: INetResource[],
        public chargingSystems?: IChargingSystem[],
        public bSCSses?: IBSCS[],
        public catalog?: ICatalog
    ) {}
}
