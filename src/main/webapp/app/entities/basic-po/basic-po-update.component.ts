import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBasicPO } from 'app/shared/model/basic-po.model';
import { BasicPOService } from './basic-po.service';
import { ICharacteristic } from 'app/shared/model/characteristic.model';
import { CharacteristicService } from 'app/entities/characteristic';
import { IOptionalService } from 'app/shared/model/optional-service.model';
import { OptionalServiceService } from 'app/entities/optional-service';
import { IPoService } from 'app/shared/model/po-service.model';
import { PoServiceService } from 'app/entities/po-service';
import { INetResource } from 'app/shared/model/net-resource.model';
import { NetResourceService } from 'app/entities/net-resource';
import { IChargingSystem } from 'app/shared/model/charging-system.model';
import { ChargingSystemService } from 'app/entities/charging-system';
import { IBSCS } from 'app/shared/model/bscs.model';
import { BSCSService } from 'app/entities/bscs';
import { ICatalog } from 'app/shared/model/catalog.model';
import { CatalogService } from 'app/entities/catalog';

@Component({
    selector: 'jhi-basic-po-update',
    templateUrl: './basic-po-update.component.html'
})
export class BasicPOUpdateComponent implements OnInit {
    basicPO: IBasicPO;
    isSaving: boolean;

    characteristics: ICharacteristic[];

    optionalservices: IOptionalService[];

    poservices: IPoService[];

    netresources: INetResource[];

    chargingsystems: IChargingSystem[];

    bscs: IBSCS[];

    catalogs: ICatalog[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected basicPOService: BasicPOService,
        protected characteristicService: CharacteristicService,
        protected optionalServiceService: OptionalServiceService,
        protected poServiceService: PoServiceService,
        protected netResourceService: NetResourceService,
        protected chargingSystemService: ChargingSystemService,
        protected bSCSService: BSCSService,
        protected catalogService: CatalogService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ basicPO }) => {
            this.basicPO = basicPO;
        });
        this.characteristicService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICharacteristic[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICharacteristic[]>) => response.body)
            )
            .subscribe((res: ICharacteristic[]) => (this.characteristics = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.optionalServiceService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IOptionalService[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOptionalService[]>) => response.body)
            )
            .subscribe((res: IOptionalService[]) => (this.optionalservices = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.poServiceService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPoService[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPoService[]>) => response.body)
            )
            .subscribe((res: IPoService[]) => (this.poservices = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.netResourceService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<INetResource[]>) => mayBeOk.ok),
                map((response: HttpResponse<INetResource[]>) => response.body)
            )
            .subscribe((res: INetResource[]) => (this.netresources = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.chargingSystemService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IChargingSystem[]>) => mayBeOk.ok),
                map((response: HttpResponse<IChargingSystem[]>) => response.body)
            )
            .subscribe((res: IChargingSystem[]) => (this.chargingsystems = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.bSCSService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBSCS[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBSCS[]>) => response.body)
            )
            .subscribe((res: IBSCS[]) => (this.bscs = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.catalogService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICatalog[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICatalog[]>) => response.body)
            )
            .subscribe((res: ICatalog[]) => (this.catalogs = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.basicPO.id !== undefined) {
            this.subscribeToSaveResponse(this.basicPOService.update(this.basicPO));
        } else {
            this.subscribeToSaveResponse(this.basicPOService.create(this.basicPO));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBasicPO>>) {
        result.subscribe((res: HttpResponse<IBasicPO>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCharacteristicById(index: number, item: ICharacteristic) {
        return item.id;
    }

    trackOptionalServiceById(index: number, item: IOptionalService) {
        return item.id;
    }

    trackPoServiceById(index: number, item: IPoService) {
        return item.id;
    }

    trackNetResourceById(index: number, item: INetResource) {
        return item.id;
    }

    trackChargingSystemById(index: number, item: IChargingSystem) {
        return item.id;
    }

    trackBSCSById(index: number, item: IBSCS) {
        return item.id;
    }

    trackCatalogById(index: number, item: ICatalog) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
