import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IChargingSystem } from 'app/shared/model/charging-system.model';
import { ChargingSystemService } from './charging-system.service';
import { IBasicPO } from 'app/shared/model/basic-po.model';
import { BasicPOService } from 'app/entities/basic-po';

@Component({
    selector: 'jhi-charging-system-update',
    templateUrl: './charging-system-update.component.html'
})
export class ChargingSystemUpdateComponent implements OnInit {
    chargingSystem: IChargingSystem;
    isSaving: boolean;

    basicpos: IBasicPO[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected chargingSystemService: ChargingSystemService,
        protected basicPOService: BasicPOService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ chargingSystem }) => {
            this.chargingSystem = chargingSystem;
        });
        this.basicPOService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBasicPO[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBasicPO[]>) => response.body)
            )
            .subscribe((res: IBasicPO[]) => (this.basicpos = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.chargingSystem.id !== undefined) {
            this.subscribeToSaveResponse(this.chargingSystemService.update(this.chargingSystem));
        } else {
            this.subscribeToSaveResponse(this.chargingSystemService.create(this.chargingSystem));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IChargingSystem>>) {
        result.subscribe((res: HttpResponse<IChargingSystem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBasicPOById(index: number, item: IBasicPO) {
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
