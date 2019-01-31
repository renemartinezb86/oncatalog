import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBasicPO } from 'app/shared/model/basic-po.model';
import { BasicPOService } from './basic-po.service';
import { ICatalog } from 'app/shared/model/catalog.model';
import { CatalogService } from 'app/entities/catalog';
import { ICharacteristic } from 'app/shared/model/characteristic.model';
import { CharacteristicService } from 'app/entities/characteristic';
import { IOptionalService } from 'app/shared/model/optional-service.model';
import { OptionalServiceService } from 'app/entities/optional-service';

@Component({
    selector: 'jhi-basic-po-update',
    templateUrl: './basic-po-update.component.html'
})
export class BasicPOUpdateComponent implements OnInit {
    basicPO: IBasicPO;
    isSaving: boolean;

    catalogs: ICatalog[];

    characteristics: ICharacteristic[];

    optionalservices: IOptionalService[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected basicPOService: BasicPOService,
        protected catalogService: CatalogService,
        protected characteristicService: CharacteristicService,
        protected optionalServiceService: OptionalServiceService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ basicPO }) => {
            this.basicPO = basicPO;
        });
        this.catalogService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICatalog[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICatalog[]>) => response.body)
            )
            .subscribe((res: ICatalog[]) => (this.catalogs = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    trackCatalogById(index: number, item: ICatalog) {
        return item.id;
    }

    trackCharacteristicById(index: number, item: ICharacteristic) {
        return item.id;
    }

    trackOptionalServiceById(index: number, item: IOptionalService) {
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
