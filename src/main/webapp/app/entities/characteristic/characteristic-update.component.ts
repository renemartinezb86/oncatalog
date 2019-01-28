import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICharacteristic } from 'app/shared/model/characteristic.model';
import { CharacteristicService } from './characteristic.service';
import { IBasicPO } from 'app/shared/model/basic-po.model';
import { BasicPOService } from 'app/entities/basic-po';

@Component({
    selector: 'jhi-characteristic-update',
    templateUrl: './characteristic-update.component.html'
})
export class CharacteristicUpdateComponent implements OnInit {
    characteristic: ICharacteristic;
    isSaving: boolean;

    basicpos: IBasicPO[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected characteristicService: CharacteristicService,
        protected basicPOService: BasicPOService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ characteristic }) => {
            this.characteristic = characteristic;
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
        if (this.characteristic.id !== undefined) {
            this.subscribeToSaveResponse(this.characteristicService.update(this.characteristic));
        } else {
            this.subscribeToSaveResponse(this.characteristicService.create(this.characteristic));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICharacteristic>>) {
        result.subscribe((res: HttpResponse<ICharacteristic>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
